<template>
    <div>
      <div style="padding: 10px 0">
        <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
          <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
            <el-option label="uid" value="id"></el-option>
            <el-option label="用户名" value="account"></el-option>
            <el-option label="昵称" value="nickname"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click='findbyinput'></el-button>
        </el-input>
      </div>
      <div class="pd-10">
        <el-popconfirm style="margin-left: 5px;margin-right: 5px"
                       confirm-button-text='好的'
                       cancel-button-text='我在想想'
                       icon="el-icon-info"
                       icon-color="red"
                       title="您确定删除吗？"
                       @confirm="delmore">
          <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove" style="margin-left: 5px"></i></el-button>
        </el-popconfirm>
      </div>
      <el-table :data="tableData" border stripe :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}"  @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55"></el-table-column>
        <el-table-column prop="id" label="uid" width="140">
        </el-table-column>
        <el-table-column prop="account" label="用户名" width="120">
        </el-table-column>
        <el-table-column prop="nickname" label="昵称">
        </el-table-column>
        <el-table-column prop="email" label="邮箱">
        </el-table-column>
        <el-table-column prop="mobilePhoneNumber" label="电话号码">
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
      <el-dialog title="修改用户(修改后请单击确定保存)" :visible.sync="dialogvisible" width="30%">
        <el-form :model="form" status-icon  ref="form">
          <el-form-item label="用户名" :label-width="formLabelWidth" prop="account">
            <el-input v-model="form.account" autocomplete="off" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="昵称" :label-width="formLabelWidth" prop="nickname">
            <el-input v-model="form.nickname" autocomplete="off" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" :label-width="formLabelWidth">
            <el-input v-model="form.email" autocomplete="off" :disabled="true">
            </el-input>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth">
            <el-input v-model="form.mobilePhoneNumber" autocomplete="off" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="封禁状态" :label-width="formLabelWidth">
            <el-switch
                style="display: block"
                v-model="form.deleted"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="是"
                inactive-text="否">
            </el-switch>
          </el-form-item>
          <el-form-item label="权限" :label-width="formLabelWidth">
            <el-radio-group v-model="form.admin">
              <el-radio :label="0">普通会员</el-radio>
              <el-radio :label="1">高级会员</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogvisible = false">取 消</el-button>
          <el-button type="primary" @click="save()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
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
        account: '',
        nickname: '',
        email: '',
        mobilePhoneNumber: '',
        deleted: false,
        lastLogin: '',
        admin: ''
      },
      formLabelWidth: '120px',
      checkPass:'',
      multipleSelection: []
    }
  },
  methods: {
    load() {
      request.get("/users", {
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
        request.get("/users/find", {
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
    save() {
      request.post("/users",this.form).then(res=>{
          if (res.data.code==200){
            this.$message.success("修改成功");
            this.dialogvisible = false
            this.setinitial()
            this.load()
          }else{
            this.$message.error(res.data.msg)
          }
      })

    },
    updatebyid(row){
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogvisible = true
    },
    del(ID){
      request.delete("/users/"+ID).then(
          res => {
            if (res.data.code==200){
              this.$message.success("删除成功");
              this.setinitial()
              this.load()
            }else{
              this.$message.error("删除失败")
            }
          }
      )
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    delmore(){
      let ids = this.multipleSelection.map(v=>v.id)//将对象数组改成类似[1,2,3]
      request.post("/users/deletemore",ids).then(
          res => {
            if (res.data.code==200){
              this.$message.success("批量删除成功");
              this.setinitial()
              this.load()
            }else{
              this.$message.error("批量删除失败")
            }
          }
      )
    }
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