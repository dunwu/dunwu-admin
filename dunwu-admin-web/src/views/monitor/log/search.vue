<template>
  <div v-if="crud.props.searchToggle">
    <el-input
      v-model="query.blurry"
      clearable
      size="small"
      placeholder="请输入你要搜索的内容"
      style="width: 200px;"
      class="filter-item"
    />
    <date-range-picker v-model="query.createTime" class="date-item" />
    <queryOperation />
  </div>
</template>

<script>
import { header } from '@crud/crud'
import queryOperation from '@crud/Query.operation'
import DateRangePicker from '@/components/DateRangePicker'
export default {
  components: { queryOperation, DateRangePicker },
  mixins: [header()]
}
</script>
