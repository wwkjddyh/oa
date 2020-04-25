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
  background-color: #fbbda4;
  border-color:#fbbda4;
}

.el-button--primary.is-disabled, .el-button--primary.is-disabled:active, .el-button--primary.is-disabled:focus, .el-button--primary.is-disabled:hover {
  background-color: #fbdacdcf;
  border-color: #fbdacdcf;
}
</style>
