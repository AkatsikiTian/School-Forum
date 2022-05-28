<template>
  <div>
    <div style="padding: 10px 0">
      <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
        <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
          <el-option label="帖子id" value="wait_article.id"></el-option>
          <el-option label="帖子标题" value="wait_article.title"></el-option>
          <el-option label="关键词" value="wait_article.summary"></el-option>
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
<!--        <div>-->
<!--          <div v-html="contentH">-->
<!--            {{contentH}}-->
<!--          </div>-->
<!--        </div>-->
        <div id="placeholder" style="visibility: hidden;height: 89px;display: none;"></div>
        <markdown-editor :editor="editor" class="me-write-editor"></markdown-editor>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="noAdd()">返回</el-button>
          <el-button type="primary" @click="addArticle(articleId)">审核通过</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import MarkdownEditor from "@/components/MarkdownEditor";

export default {
  name: "WaitArticle",
  data(){
    return{
      tableData: null, //表单数据
      currentPage: 1, //页号
      pageSize: 5, //每页个数
      total: 0, //总击条数
      multipleSelection: [], //批量
      input: "", //模糊查询关键词
      select: "", //模糊查询类别
      value: true, //忘了
      dialogVisible: false, //控制dialog
      articleId: "", //正在查看的帖子id
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
      request.get("/waitarticle/page", {
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
        request.get("/waitarticle/find", {
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
      request.get("/waitarticle/findcontent/"+id).then(res=>{
        this.articleId = id
        this.editor.value = res.data.data
        this.dialogVisible = true
      })
    },
    delArticle(id){
      request.delete("/waitarticle/delArticle/"+id).then(res=>{
        this.load()
      })
    },
    changWeight(state,id){
      if(state==true){
        request.post("/waitarticle/updateArticle/"+id).then(res=>{
          console.log()
        })
      }if(state==false){
        request.post("/waitarticle/updateArticle1/"+id).then(res=>{
          console.log()
        })
      }
    },
    noAdd(){
      this.dialogVisible = false
      this.articleId = " "
    },
    addArticle(articleId){
      request.post("/waitarticle/add/"+articleId).then(res=>{
        this.$message.success("审核通过")
        this.dialogVisible = false
        this.load()
      })
    }
  },
  components:{
    'markdown-editor': MarkdownEditor
  }
}
</script>

<style scoped>

</style>