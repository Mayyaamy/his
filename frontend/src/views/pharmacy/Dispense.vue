<template>
  <el-card class="page-card">
    <div class="page-toolbar">
      <el-button type="primary" @click="load">刷新</el-button>
    </div>

    <el-table :data="records" border stripe row-key="id">
      <el-table-column type="expand">
        <template #default="{ row }">
          <el-table :data="row.items" border size="small">
            <el-table-column prop="drugName" label="药品" width="200" />
            <el-table-column prop="drugSpec" label="规格" width="120" />
            <el-table-column prop="drugUnit" label="单位" width="60" />
            <el-table-column prop="dosage" label="剂量" width="100" />
            <el-table-column prop="frequency" label="频次" width="120" />
            <el-table-column prop="days" label="天数" width="60" />
            <el-table-column prop="qty" label="数量" width="60" />
            <el-table-column label="单价" width="100">
              <template #default="{ row: r }">¥{{ r.unitPrice }}</template>
            </el-table-column>
            <el-table-column label="小计" width="100">
              <template #default="{ row: r }">¥{{ r.subtotal }}</template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column prop="billNo" label="账单编号" width="160" />
      <el-table-column prop="patientName" label="患者" width="120" />
      <el-table-column prop="patientGender" label="性别" width="60" />
      <el-table-column prop="doctorName" label="医生" width="120" />
      <el-table-column prop="regNo" label="挂号号" width="160" />
      <el-table-column label="金额" width="120">
        <template #default="{ row }">¥{{ row.totalAmount }}</template>
      </el-table-column>
      <el-table-column label="发药状态" width="100">
        <template #default="{ row }">
          <el-tag type="warning">待发药</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100" fixed="right">
        <template #default="{ row }">
          <el-popconfirm title="确认发药？将扣减库存" @confirm="onDispense(row)">
            <template #reference>
              <el-button link type="primary">发药</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { listDispenses, dispense } from '@/api/dispense'

const records = ref([])

async function load() {
  records.value = await listDispenses()
}

async function onDispense(row) {
  await dispense(row.id)
  ElMessage.success('发药成功')
  load()
}

onMounted(load)
</script>
