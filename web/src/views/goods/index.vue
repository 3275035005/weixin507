<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="商品名称">
        <el-input v-model="dataVo.name" placeholder="请输入商品名称"/>
      </el-form-item>
      <el-form-item label="商品分类">
        <el-select v-model="dataVo.classifyId" placeholder="请选择商品分类">
          <el-option v-for="item in goodsClassifyList" :key="item.id" :label="item.title" :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      empty-text="暂无数据"
    >

      <el-table-column
        label="序号"
        width="60"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}  <!--序号,算法-->
        </template>
      </el-table-column>
      <el-table-column align="center" label="商品封面">
        <template slot-scope="scope">
          <img
            style="width: 80px; height: 80px"
            :src="scope.row.image"/>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品名称"/>
      <el-table-column prop="content" label="商品介绍">
        <template slot-scope="scope">
          <truncate-text :text="scope.row.content" :maxLength="10"></truncate-text>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="商品价格"/>
      <el-table-column prop="num" label="商品库存"/>
      <el-table-column prop="userName" label="发布用户"/>
      <el-table-column prop="createTime" label="发布时间"/>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      style="padding: 30px 0; text-align: center;"
      :page-size="limit"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="getList"
    />
  </div>
</template>
<script>
import { deleteById, pageQuery } from '@/api/goods'
import { getGoodsClassifyAll } from '@/api/goodsClassify'

import TruncateText from '@/components/TruncateText'
export default {
  components: {
    TruncateText
  },

  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 10, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      goodsClassifyList: []
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.init()
    this.getList()
  },
  methods: { // 创建具体的方法, 定义的方法
    init() {
      getGoodsClassifyAll().then(res => {
        this.goodsClassifyList = res.data.list
      })
    },
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          this.list = response.data.rows.records
          this.total = response.data.rows.total
        })
    },

    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        deleteById(id)
          .then(response => { // 删除成功执行的方法
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功！'
              })
              // 刷新表格
              this.getList()
            } else {
              this.$message({
                type: 'error',
                message: '删除失败！'
              })
            }
          })
      })
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.page = 1 // 当前页
      this.limit = 10// 每页记录数
      this.dataVo = {};
      this.getList()
    }
  }
}
</script>
