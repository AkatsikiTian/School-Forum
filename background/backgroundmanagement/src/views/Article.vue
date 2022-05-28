<template>
  <div>
    <div style="padding: 10px 0">
      <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
        <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
          <el-option label="帖子id" value="article.id"></el-option>
          <el-option label="帖子标题" value="article.title"></el-option>
          <el-option label="关键词" value="article.summary"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click='findbyinput'></el-button>
      </el-input>
    </div>
    <el-table :data="tableData" border stripe :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"  @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55"></el-table-column>
      <el-table-column prop="id" label="帖子id" width="220">
      </el-table-column>
      <el-table-column prop="title" label="帖子标题" width="220">
      </el-table-column>
      <el-table-column prop="summary" label="帖子简介" width="220">
      </el-table-column>
      <el-table-column prop="account" label="作者" width="220">
      </el-table-column>
      <el-table-column prop="state" label="权重" width="100" v-if="false" >
      </el-table-column>
      <el-table-column  label="置顶" width="150">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.state"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="changWeight(scope.row.state,scope.row.id)"
            >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
          label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleCheck(scope.row.id)">查看<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='我在想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="delArticle(scope.row.id)">
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
    <div>
      <el-dialog
          title="帖子内容"
          :visible.sync="dialogVisible"
          width="60%">
        <div>
          <div id="placeholder" style="visibility: hidden;height: 89px;display: none;"></div>
          <markdown-editor :editor="editor" class="me-write-editor"></markdown-editor>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import MarkdownEditor from "@/components/MarkdownEditor";


export default {
  name: "Article",
  data(){
    return{
      tableData: null,
      currentPage: 1,
      pageSize: 5,
      total: 0,
      multipleSelection: [],
      input: "",
      select: "",
      value: true,
      dialogVisible: false,
      editor: {
        value: '',
        toolbarsFlag: false,
        subfield: false,
        defaultOpen: 'preview'
      },
    }
  },
  created() {
    this.load()
  },
  methods:{
    load() {
      request.get("/article/page", {
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(
          res => {
              console.log(res)
              this.tableData = res.data.data.data
              this.total = res.data.data.total
          }
      )
    },
    findbyinput() {
      if (this.select != "" && this.input != "") {
        request.get("/article/find", {
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
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      if(this.input!=""){
        this.findbyinput()
      }else{
        this.load()
      }
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      if(this.input!=""){
        this.findbyinput()
      }else{
        this.load()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleCheck(id){
      request.get("/article/findcontent/"+id).then(res=>{
        this.editor.value = res.data.data
        this.dialogVisible = true
      })
    },
    delArticle(id){
      request.delete("/article/delArticle/"+id).then(res=>{
        this.load()
      })
    },
    changWeight(state,id){
      if(state==true){
        request.post("/article/updateArticle/"+id).then(res=>{
          console.log()
        })
      }if(state==false){
        request.post("/article/updateArticle1/"+id).then(res=>{
          console.log()
        })
      }
    },
  },
components:{
  'markdown-editor': MarkdownEditor
}
}
</script>

<style scoped>

</style>