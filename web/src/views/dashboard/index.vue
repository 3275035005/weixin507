<template>
  <div class="dashboard-container">
    <el-row class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="user" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              用户数量
            </div>
            <count-to :start-val="0" :end-val="userNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="form" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              帖子数量
            </div>
            <count-to :start-val="0" :end-val="postNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="example" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              商品数量
            </div>
            <count-to :start-val="0" :end-val="goodsNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="form" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              订单数量
            </div>
            <count-to :start-val="0" :end-val="orderNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-card class="box-card" style="margin: 10px">
        <div slot="header" class="clearfix">
          <span>订单统计</span>
          <el-date-picker
            style="float: right;"
            v-model="date"
            type="month"
            @change="init"
            value-format="yyyy-MM"
            placeholder="选择年月">
          </el-date-picker>
        </div>
        <!-- 统计图容器 -->
        <div id="main" style="width: 100%;height: 500px;" />
      </el-card>
    </el-row>
  </div>
</template>

<script>
import echarts from "echarts";
import CountTo from 'vue-count-to'
import { getInfo, getHistogram } from '@/api/dashboard'
export default {
  name: 'Dashboard',
  components: {
    CountTo
  },
  data() {
    return {
      date: this.getDate(),
      userNumber: 0,
      postNumber: 0,
      goodsNumber: 0,
      orderNumber: 0,
      activeNames: ['1', '2', '3', '4']
    };
  },
  created() {
    this.init()
  },
  methods: {
    init(){
      getHistogram(this.date).then(res =>{
        var echarts = require('echarts')
        // 初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'))
        // 配置参数
        var option = {
          xAxis: {
            type: 'category',
            axisLine: {
              lineStyle: {
                color: '#D7DDE4'
              }
            },
            axisTick: {
              alignWithLabel: true,
              lineStyle: {
                color: '#D7DDE4'
              }
            },
            axisLabel: {
              textStyle: {
                color: '#7F8B9C'
              }
            },
            splitLine: {
              show: false,
              lineStyle: {
                color: '#F5F7F9'
              }
            },
            data: res.data.day,
          },
          yAxis: {
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#7F8B9C'
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#F5F7F9'
              }
            },
            type: 'value'
          },
          series: [{
            name: '订单量',
            data: res.data.number,
            type: 'bar',
            tooltip: true,
            smooth: true,
            symbol: 'none',
            itemStyle: {
              normal: {
                barBorderRadius: [3, 3, 0, 0],
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: 'rgba(90, 177, 239)'
                  },
                  {
                    offset: 0.5,
                    color: 'rgba(90, 177, 239)'
                  },
                  {
                    offset: 1,
                    color: 'rgba(90, 177, 239)'
                  }
                ])
              }
            }
          }],
          grid: {
            left: 25,
            right: 25,
            bottom: 5,
            top: 10,
            containLabel: true
          },
          tooltip: {
            trigger: 'axis'
          }
        }
        myChart.setOption(option)
      })

      getInfo().then(res => {
        this.userNumber = res.data.userNumber
        this.postNumber = res.data.postNumber
        this.goodsNumber = res.data.goodsNumber
        this.orderNumber = res.data.orderNumber
      })
    },
    getDate(){
      const now = new Date();

      const year = now.getFullYear();
      const month = ('0' + (now.getMonth() + 1)).slice(-2);

      return year +"-"+ month;
    },
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    }
  },

}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
