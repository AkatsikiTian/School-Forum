<template>
    <div>
      <div style="padding: 10px 0">
        <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
          <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
            <el-option label="uid" value="id"></el-option>
            <el-option label="名称" value="tagName"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click='findbyinput'></el-button>
        </el-input>
      </div>
      <div class="pd-10">
        <el-button type="primary" @click="add()">新增
          <i class="el-icon-circle-plus" style="margin-left: 5px"></i>
        </el-button>
      </div>
      <el-table :data="tableData" border stripe :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}">

        <el-table-column prop="id" label="uid" width="140">
        </el-table-column>
        <el-table-column  label="图片">
          <template slot-scope="scope">
            <el-popover placement="top-start" title="" trigger="hover">
              <el-image :src="scope.row.avatar" alt="" style="width: 150px;height: 150px"></el-image>
              <el-image slot="reference" :src="scope.row.avatar" style="width: 30px;height: 30px"></el-image>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="tagName" label="标签名称">
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="updatebyid(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='我在想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除吗？"
                @confirm="del(scope.row.id)">
              <el-button class="ml-5" type="danger" size="small" slot="reference">删除<i class="el-icon-delete"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[1, 2, 5, 10]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
      <el-dialog title="新增或修改标签(请单击确定保存)" :visible.sync="dialogvisible" width="30%">
        <el-form :model="form" status-icon  ref="form" :rules="rules">
          <el-form-item label="标签名称" :label-width="formLabelWidth" prop="tagName">
            <el-input v-model="form.tagName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="图片" :label-width="formLabelWidth" prop="avatar">
            <el-select v-model="form.avatar" placeholder="请选择图片">
              <el-option
                  v-for="item in tags"
                  :key="item.value"
                  :value="item.value">
                <el-popover placement="top-start" title="" trigger="hover">
                <el-image :src="item.value" alt="" style="width: 150px;height: 150px"></el-image>
                <el-image slot="reference" :src="item.value" style="width: 100%;height: 100%"></el-image>
                </el-popover>
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogvisible = false">取 消</el-button>
          <el-button type="primary" @click="save('form')">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Tag",
  data() {
    return {
      tableData: null,
      currentPage: 1,
      total: 0,
      pageSize: 5,
      input: "",
      select: "",
      dialogvisible: false,
      form: {
        id: '',
        avatar: '',
        tagName: ''
      },
      tags:[
          {value: '/static/tag/css.png'},
        {value: '/static/tag/js.png'},
        {value: '/static/tag/html.png'},
        {value: '/static/tag/hibernate.svg'},
        {value: '/static/tag/maven.png'},
        {value: '/static/tag/vue.png'}
      ],
      rules: {
        tagName: [
          { required: true, message: '请输入标签名', trigger: 'blur'}
        ],
        avatar: [
          {required: true,  message: '请选择图片', trigger: 'blur' }
        ]
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    load() {
      request.get("/tags", {
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(
          res => {
            this.tableData = res.data.data.data
            this.total = res.data.data.total
          }
      )

    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.load()
    },
    findbyinput() {
      if (this.select != "" && this.input != "") {
        request.get("/tags/find", {
          params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
            select: this.select,
            input: this.input
          }
        }).then(
            res => {
              this.tableData = res.data.data.data
              this.total = res.data.data.total
            }
        )
      } else {
        this.load()
      }
    },
    setinitial(){
      this.currentPage=1
      this.pageSize=5
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/tags", this.form).then(
              res => {
                if (res.data.code==200){
                  this.$message.success("保存成功");
                  this.dialogvisible = false
                  this.setinitial()
                  this.load()
                }else{
                  this.$message.error("保存失败，有信息重复")
                }
                this.resetForm()
              }
          )
        } else {
          this.$message.warning("尚有未填的部分");
          return false;
        }
      });
    },
    updatebyid(row){
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogvisible = true
    },
    add(){
      this.resetForm()
      this.dialogvisible = true
    },
    resetForm() {
      this.form={
        tagName: '',
        avatar:''
      };
    },
    del(ID){
      request.delete("/tags/"+ID).then(
          res => {
            if (res.data.code==200){
              this.$message.success("删除成功");
              this.setinitial()
              this.load()
            }else{
              this.$message.error(res.data.msg)
            }
          }
      )
    },
  },
  created() {
    this.load()
  }
}

</script>

<style>
.el-select .el-input {
  width: 130px;
}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>