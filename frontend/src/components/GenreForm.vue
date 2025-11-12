<template>
  <div class="modal">
    <div class="modal-content">
      <h3>Add Genre</h3>

      <input v-model="form.name" placeholder="Genre Name" />
      <textarea placeholder="Description"></textarea>

      <div class="actions">
        <button class="cancel" @click="$emit('close')">Cancel</button>
        <button class="save" @click="save">Save</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, defineEmits } from 'vue'

const emit = defineEmits(['close', 'save'])

const form = reactive({
  name: '',
})

function save() {
  emit('save', { ...form })
   emit('close') 
  
}
</script>

<style scoped>
.modal {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.6);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  min-width: 320px;
}
textarea {
  width: 100%;
  height: 80px;
  margin-top: 8px;
  padding: 8px;
}
.actions {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
.cancel {
  background: #ccc;
  border: none;
  padding: 8px 12px;
  border-radius: 6px;
}
.save {
  background: #0d6efd;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 6px;
}
</style>
