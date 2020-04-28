<template>
  <div id="app">
    <transition>
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </transition>
  </div>
</template>

<style>
html,body,#app {
  width: 100%;
  height: 100%;
  background-color: #F3F6F8;
  margin: 0px;
  padding: 0px;
}
.el-button--primary {
  background-color: #f3f3f3 !important;
  border-color:#f3f3f3 !important;
  color: #827F7A !important;
}

.el-button--primary.is-disabled, .el-button--primary.is-disabled:active, .el-button--primary.is-disabled:focus, .el-button--primary.is-disabled:hover {
  background-color: #f3f3f3 !important;
  border-color: #f3f3f3 !important;
}
.el-button--primary:focus, .el-button--primary:hover{
  background-color: #c9c7c6 !important;
  border-color:#c9c7c6 !important;

}
.body-container .el-dropdown {
  color: black !important;
}

</style>
