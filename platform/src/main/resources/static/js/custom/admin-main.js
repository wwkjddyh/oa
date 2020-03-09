new Vue({
    el: '#admin-main',
    watch: {
        // filterText(val) {
        //     this.$refs.tree2.filter(val);
        // },

        /**
         * 监控showContent变化
         * @param val
         * @param oldVal
         */
        showContent(val,oldVal) {
            let that = this;
            /*let formArticleObj = document.getElementById('formArticle'),
                formJobCompanyObj = document.getElementById('formJobCompany'),
                statUserObj = document.getElementById('statUser');

            if(val === 'statUser') {
                that.createChart('statUser');
                statUserObj.style.display = 'block';
            }
            else {
                statUserObj.style.display = 'none';
            }*/

            /*使用v-show方式*/
            switch (val) {
                case 'categories':
                    that.getAllCategoryTypes();
                    that.loadCategories('','',1, that.pager.category.pageSize);
                    break;
                case 'categoryTypes':
                    that.loadCategoryTypes('', 1, that.pager.categoryType.pageSize);
                    break;
                case 'langConfs':
                    that.loadLangConfs('', 1, that.pager.langConf.pageSize);
                    break;

                case 'modules':
                    that.getAllAuthModules();
                    that.loadAuthModules('',1, that.pager.authModule.pageSize);
                    break;
                case 'roles':
                    that.getAllAuthModules();
                    that.loadAuthRoles('',1, that.pager.authRole.pageSize);
                    break;
                case 'memberUsers':
                    that.getAllAuthRole();
                    that.loadMemberUsers('','',1, that.pager.user.pageSize);
                    break;
                case 'sysUsers':
                    that.getAllAuthRole();
                    that.loadSysUsers('',1, that.pager.sysUser.pageSize);
                    break;
      
            }
        },

        /**
         * 监测cronConfig的值变化
         * @param val 新值
         * @param oldVal 原值
         */
        cronConfig(val, oldVal) {

        },

    },
    data: {
        def_menu_id: 'firstPage',
        currAction: 'append',
        isCollapse: false,
        activeTabName: 'first',
        cronTabActiveName: 'second',    /*cron表达式tabs设置*/
        showContent: 'firstPage',
        chartIds: {
            statUser: 'statUserChartId',
        },
        ueditors: {
            article: null,
            jobCompany: null
        },
        ueditorConfig : {
            //focus时自动清空初始化时的内容
            autoClearinitialContent:true,
            //关闭字数统计
            wordCount:false,
            //关闭elementPath
            elementPathEnabled:false,
            //默认的编辑区域高度
            initialFrameWidth:650,
            initialFrameHeight:300,
            initialContent:'',
            //  scaleEnabled:true   //设置不自动调整高度
            toolbars: [
                [
                    'source', //源代码
                    'cleardoc', //清空文档
                    'insertcode', //代码语言
                    'fontfamily', //字体
                    'fontsize', //字号
                    'paragraph', //段落格式
                    'customstyle', //自定义标题
                    '|',
                    'simpleupload', //单图上传
                    'insertimage', //多图上传
                    'emotion', //表情
                    'time', //时间
                    'date', //日期
                    'map', //Baidu地图
                    'edittip ', //编辑提示
                    'autotypeset', //自动排版
                    'touppercase', //字母大写
                    'tolowercase', //字母小写
                    'background', //背景
                    'template', //模板
                    'scrawl', //涂鸦
                    '|',
                    'anchor', //锚点
                    'undo', //撤销
                    'redo', //重做
                    'pagebreak', //分页
                    'bold', //加粗
                    'indent', //首行缩进
                    'snapscreen', //截图
                    'italic', //斜体
                    'underline', //下划线
                    'strikethrough', //删除线
                    'subscript', //下标
                    'fontborder', //字符边框
                    'superscript', //上标
                    'formatmatch', //格式刷
                    'blockquote', //引用
                    'pasteplain', //纯文本粘贴模式
                    'selectall', //全选
                    'link', //超链接
                    'horizontal', //分隔线
                    'removeformat', //清除格式
                    'unlink', //取消链接
                    '|',
                    'insertrow', //前插入行
                    'insertcol', //前插入列
                    'mergeright', //右合并单元格
                    'mergedown', //下合并单元格
                    'deleterow', //删除行
                    'deletecol', //删除列
                    'splittorows', //拆分成行
                    'splittocols', //拆分成列
                    'splittocells', //完全拆分单元格
                    'deletecaption', //删除表格标题
                    'inserttitle', //插入标题
                    'mergecells', //合并多个单元格
                    'deletetable', //删除表格
                    'insertparagraphbeforetable', //"表格前插入行"
                    'edittable', //表格属性
                    'edittd', //单元格属性
                    'spechars', //特殊字符
                    'searchreplace', //查询替换
                    'justifyleft', //居左对齐
                    'justifyright', //居右对齐
                    'justifycenter', //居中对齐
                    'justifyjustify', //两端对齐
                    'forecolor', //字体颜色
                    'backcolor', //背景色
                    'insertorderedlist', //有序列表
                    'insertunorderedlist', //无序列表
                    'fullscreen', //全屏
                    'directionalityltr', //从左向右输入
                    'directionalityrtl', //从右向左输入
                    'rowspacingtop', //段前距
                    'rowspacingbottom', //段后距
                    'insertframe', //插入Iframe
                    'imagenone', //默认
                    'imageleft', //左浮动
                    'imageright', //右浮动
                    'imagecenter', //居中
                    'lineheight', //行间距
                    'inserttable', //插入表格
                    'charts', // 图表
                    'preview', //预览
                ]
            ]
        },

        formUser: {},
        formSysUser: {},
        formUserType: {},
        formUserDetail: {},
        formMember: {},
        formOperateLog: {},

        formAuthUserRole: {},
        formAuthRoleModule: {},
        formAuthRole: {},
        formAuthModule: {},
        formAuthUserRoleModule: {},
        formCategory: {},
        formCategoryType: {},
        formDict: {},
        formLangConf: {},

        formSearchAuthUserRole: {},
        formSearchAuthRoleModule: {},
        formSearchAuthRole: {},
        formSearchAuthModule: {},
        formSearchAuthUserRoleModule: {},
        formSearchMemberUser: {},
        formSearchCategory: {},
        formSearchCategoryType: {},
        formSearchDict: {},
        formSearchLangConf: {},

        dialogShow: {},
        memberUsers: [],
        sysUsers: [],
        authModules: [],

        allRoles: [],
        allModules: [],
        articleCategories: [],
        allInsectCategories: [],
        allCategoryTypes: [],
        allLangConfs: [],
        allTitleTypes: [],
        allLangAndType: {},
        allTitleConfs: {},
        roleModules: [],
        userRoles: [],
        roles: [],
        categories: [],
        categoryTypes : [],
        langConfs: [],
        continent: '',
        nation: '',
        rules: {},
        chartsData: [
            {
                chartType: 'column',
                title: '近4月用户数',
                subtitle: '',
                categories : ['一般用户', '一般会员', '猎头', '企业用户', 'VIP'],
                series : [{
                    name: '2018/05',
                    data: [3000, 200, 10, 5, 20]
                }, {
                    name: '2018/06',
                    data: [5000, 500, 50, 100, 50]
                }, {
                    name: '2018/07',
                    data: [2000, 1000, 37, 7, 300]
                }, {
                    name: '2018/08',
                    data: [1216, 1001, 32, 738, 400]
                }],
            },
        ],

        isIndeterminate: {
            roleModule: true,
            userRole: true,
            sysUserRole: true,
        },
        checkBoxAll: {
            roleModule: false,
            userRole: false,
            sysUserRole: false,
        },
        checkBoxOptions: {
            roleModule: [],
            userRole: [],
            sysUserRole: [],
        },
        /**
         * 复选中关联id
         */
        checkBoxRelationId: {
            roleModule: '',
            userRole: '',
            sysUserRole: '',
        },

        pager: {
            authModule: {
                //搜索条件
                criteria: '',

                //默认每页数据量
                pageSize: 10,

                //默认高亮行数据id
                highlightId: -1,

                //当前页码
                currentPage: 1,

                //查询的页码
                start: 1,

                //默认数据总数
                totalCount: 1000,
            },
            authRole: {
                //搜索条件
                criteria: '',

                //默认每页数据量
                pageSize: 10,

                //默认高亮行数据id
                highlightId: -1,

                //当前页码
                currentPage: 1,

                //查询的页码
                start: 1,

                //默认数据总数
                totalCount: 1000,
            },
            user: {
                //搜索条件
                criteria: '',

                //类型id
                typeId: '',

                //默认每页数据量
                pageSize: 10,

                //默认高亮行数据id
                highlightId: -1,

                //当前页码
                currentPage: 1,

                //查询的页码
                start: 1,

                //默认数据总数
                totalCount: 1000,
            },
            sysUser: {
                //搜索条件
                criteria: '',

                //默认每页数据量
                pageSize: 10,

                //默认高亮行数据id
                highlightId: -1,

                //当前页码
                currentPage: 1,

                //查询的页码
                start: 1,

                //默认数据总数
                totalCount: 1000,
            },
            category: {
                //搜索条件
                criteria: '',

                //默认每页数据量
                pageSize: 10,

                //默认高亮行数据id
                highlightId: -1,

                //当前页码
                currentPage: 1,

                //查询的页码
                start: 1,

                //默认数据总数
                totalCount: 1000,
            },
            categoryType: {
                //搜索条件
                criteria: '',

                //默认每页数据量
                pageSize: 10,

                //默认高亮行数据id
                highlightId: -1,

                //当前页码
                currentPage: 1,

                //查询的页码
                start: 1,

                //默认数据总数
                totalCount: 1000,
            },
            langConf: {
                //搜索条件
                criteria: '',

                //默认每页数据量
                pageSize: 10,

                //默认高亮行数据id
                highlightId: -1,

                //当前页码
                currentPage: 1,

                //查询的页码
                start: 1,

                //默认数据总数
                totalCount: 1000,
            },
        },
    },
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        handleNavMenu(tab, event, indexPath) {    //处理菜单选择
            console.log("handleNavMenu:", tab, event, indexPath);
            this.showContent = tab;
        },
        resetForm(formName) {
            console.log('resetForm',formName);
            this.$refs[formName].resetFields();
            if(this.showContent === 'formArticle') {
                setTimeout(function() {
                    this.ueditors.article.setContent("", false);
                }, 500);
            }
            /*
            else if(this.showContent === 'formJobCompany') {
                this.ueditors.jobCompany.setContent("", false);
            }*/
            //勿删，特意写下此句
            console.log('resetForm-formName,',formName,new Date().getTime());
        },
        submitForm(formName) {
            let that = this;
            console.log("submit-formName:",formName);
            that.$refs[formName].validate((valid) => {
                if (valid) {
                    that.$confirm('是否确认提交？', '警告', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning',
                        callback: action => {
                            if(action === "cancel"){
                                that.$message({
                                    type: 'info',
                                    message: "取消提交"
                                });
                            }else{
                                switch (formName) {
                                    case 'formAuthRole' : that.sumbitAuthRole(); break;

                                    case 'formAuthModule': that.submitAuthModule(); break;
                                    case 'formUser': that.submitUser(false); break;
                                    case 'formSysUser': that.submitUser(true); break;

                                    case 'formCategoryType': that.submitCategoryType(); break;
                                    case 'formCategory': that.submitCategory(); break;

                                    case 'formLangConf': that.submitLangConf(); break;
                                    default: break;
                                }
                                //提交成功之后
                                that.resetForm(formName);
                            }
                        }
                    });

                } else {
                    console.log(formName,'error submit!!');
                    return false;
                }
            });
            //勿删，特意写下此句
            console.log('submitForm-formName,',formName,new Date().getTime());
        },
        searchForm(formName) {
            let that = this;
            switch (formName) {
                case 'formSearchAuthModule':
                    that.loadAuthModules(that.formSearchAuthModule.moduleName,1,that.pager.authModule.pageSize);
                    break;
                case 'formSearchMemberUser':
                    that.loadMemberUsers(that.formSearchMemberUser.name,that.formSearchMemberUser.typeId,1,that.pager.user.pageSize);
                    break;

                case 'formSearchCategoryType':
                    that.loadCategoryTypes(that.formSearchCategoryType.name, 1, that.pager.categoryType.pageSize);
                    break;
                case 'formSearchCategory':
                    that.loadCategories(that.formSearchCategory.name, that.formSearchCategory.typeId, 1, that.pager.category.pageSize);
                    break;
                case 'formSearchLangConf':
                    that.loadLangConfs(that.formSearchLangConf.name, 1, that.pager.langConf.pageSize);
                    break;

            }
        },

        /**
         * 响应消息处理
         * @param response 响应对象（异步返回数据: 需有data=>{keys: code、data}）
         * @param title 标题
         * @param operName 操作名称
         * @param successCallback 成功处理
         * @param failureCallback 失败处理
         * @param repeatCallback 重复参数处理
         * @param paramErrorCallback 参数异常处理
         */
        responseMessageHandler(response, title, operName, successCallback, failureCallback, repeatCallback, paramErrorCallback) {
            let that = this;
            let codeVal = parseInt(response.data.code);
            let messageContent = '', messageType = 'info';
            switch (codeVal) {
                case 200:
                    if(typeof successCallback === 'function') {
                        successCallback();
                    }
                    messageContent = title + operName+'成功!';
                    messageType = 'success';
                    break;
                case 401:
                    messageContent = '请登录!';
                    messageType = 'warning';
                    break
                case 411:
                    if(typeof paramErrorCallback === 'function') {
                        paramErrorCallback();
                    }
                    messageContent = '请求参数异常!';
                    messageType = 'warning';
                    break;
                case 555:
                    if(typeof repeatCallback === 'function') {
                        repeatCallback();
                    }
                    messageContent = '"'+response.data.data+'"与已有数据重复!';
                    messageType = 'warning';
                    break;
                default:
                    if(typeof failureCallback === 'function') {
                        failureCallback();
                    }
                    messageContent = title + operName + '失败!';
                    messageType = 'error';
                    break;
            }
            that.$message({
                message: messageContent,
                type: messageType
            });
        },

        /**
         * 用户信息提交
         */
        submitUser(isAdmin) {
            let that = this;
            let userType = isAdmin ? '(系统)' : '(会员)';
            let params = new URLSearchParams();
            params.append('name',that.formUser.name || '');
            params.append('nickname',that.formUser.nickname || '');
            params.append('passwordOrgi',that.formUser.passwordOrgi || '123456');
            params.append('typeId',that.formUser.typeId || '');
            params.append('isHeadhunting',that.formUser.isHeadhunting || 0);
            if(that.currAction === 'append') {
                axios.post("/api/auth/saveUserBaseInfo",params)
                    .then(function(response){
                        if(parseInt(response.data.code) === 200){
                            that.dialogShow.memberUser = false;
                            that.loadMemberUsers('','',1,that.pager.authModule.pageSize);
                            that.$message({
                                message: userType + '用户信息添加成功!',
                                type: 'success'
                            });
                        }
                    }).catch(function(err){
                    console.warn(err);
                });
            }
            else {
                params.append('updateLoginTime',1);
                params.append('status',that.formUser.status);
                params.append('id',that.formUser.id);

                axios.post("/api/auth/updateUserBaseInfoById",params)
                    .then(function(response){
                        if(parseInt(response.data.code) === 200){
                            that.dialogShow.memberUser = false;
                            that.loadMemberUsers('','',1,that.pager.authModule.pageSize);
                            that.$message({
                                message: userType + '用户信息修改成功!',
                                type: 'success'
                            });
                        }
                    }).catch(function(err){
                    console.warn(err);
                });
            }
        },

        /**
         * 权限模块信息提交
         */
        submitAuthModule() {
            let that = this;
            let params = new URLSearchParams();
            let operName = '添加';
            params.append('parentId',that.formAuthModule.parentId || '');
            params.append('moduleName',that.formAuthModule.moduleName || '');
            params.append('moduleDesc',that.formAuthModule.moduleDesc || '');
            params.append('moduleUrl',that.formAuthModule.moduleUrl || '');
            params.append('isLeaf',that.formAuthModule.isLeaf || '1');
            params.append('recordFlag',1);
            if(that.currAction === 'edit') {
                operName = '修改';
                params.append('moduleId',that.formAuthModule.moduleId);
            }
            axios.post("/api/user/saveModule",params)
                .then(function(response){
                    that.responseMessageHandler(response, '模块信息', operName, function() {
                        that.dialogShow.authModule = false;
                        that.pager.authModule.currentPage = 1;
                        that.loadAuthModules('',1,that.pager.authModule.pageSize);
                    });
                }).catch(function(err){
                console.warn(err);
            });
        },

        /**
         * 角色信息提交
         */
        sumbitAuthRole() {
            let that = this;
            let params = new URLSearchParams();
            let operName = '添加';
            params.append('roleName',that.formAuthRole.roleName);
            params.append('roleDesc',that.formAuthRole.roleDesc);
            if(that.currAction === 'edit') {
                operName = '修改';
                params.append('roleId',that.formAuthRole.roleId || '');
            }
            axios.post("/api/user/saveRole",params)
                .then(function(response){
                    that.responseMessageHandler(response, '角色信息', operName,
                        function(){
                            that.dialogShow.role = false;
                            that.pager.authRole.currentPage = 1;
                            that.loadAuthRoles('',1,that.pager.authRole.pageSize);
                        });
                }).catch(function(err){
                console.warn(err);
            });
        },

        /**
         * 分类类别信息提交
         */
        submitCategoryType() {
            let that = this;
            let params = new URLSearchParams();
            let operName = '添加';
            params.append('name',that.formCategoryType.name);
            params.append('desc',that.formCategoryType.desc);
            if(that.currAction === 'edit') {
                operName = '修改';
                params.append('recordId',that.formCategoryType.recordId || '');
            }
            axios.post("/api/category/saveCategoryType",params)
                .then(function(response){
                    that.responseMessageHandler(response, '分类类别信息', operName, function() {
                        that.dialogShow.categoryType = false;
                        that.pager.categoryType.currentPage = 1;
                        that.loadCategoryTypes('',1,that.pager.categoryType.pageSize);
                    });
                }).catch(function(err){
                console.warn(err);
            });
        },

        /**
         * 分类信息提交
         */
        submitCategory() {
            let that = this;
            let params = new URLSearchParams();
            let operName = '添加';
            params.append('typeId',that.formCategory.typeId);
            params.append('name',that.formCategory.name);
            params.append('desc',that.formCategory.desc);
            if(that.currAction === 'edit') {
                operName = '修改';
                params.append('recordId',that.formCategory.recordId || '');
            }
            axios.post("/api/category/saveCategory",params)
                .then(function(response){
                    that.responseMessageHandler(response, '分类信息', operName, function() {
                        that.dialogShow.category = false;
                        that.pager.category.currentPage = 1;
                        that.loadCategories('','', 1 ,that.pager.category.pageSize);
                    });
                }).catch(function(err){
                console.warn(err);
            });
        },

        /**
         * 语言配置信息提交
         */
        submitLangConf() {
            let that = this;
            let params = new URLSearchParams();
            let operName = '添加';
            params.append('name',that.formLangConf.name);
            params.append('desc',that.formLangConf.desc);
            if(that.currAction === 'edit') {
                operName = '修改';
                params.append('recordId',that.formLangConf.recordId || '');
            }
            axios.post("/api/title/saveLangConf",params)
                .then(function(response){
                    that.responseMessageHandler(response, '语言配置', operName, function() {
                        that.dialogShow.langConf = false;
                        that.pager.langConf.currentPage = 1;
                        that.loadLangConfs('',1,that.pager.langConf.pageSize);
                    });
                }).catch(function(err){
                console.warn(err);
            });
        },

        show(type,scopeIndex,scopeRow) {
            let that = this;
            let rowId = scopeRow != undefined && scopeRow.id != undefined ? scopeRow.id : '';
            switch (type) {
                case 'article':
                    break;

            }
        },
        edit(type,scopeIndex, scopeRow, isAdd) {
            let that = this, entry = null, parents = [];
            isAdd = isAdd != undefined && isAdd == true? true : false;
            let rowId = scopeRow != undefined && scopeRow.id != undefined ? scopeRow.id : '';
            that.currAction = isAdd ? 'append':'edit';
            console.log('currAction',that.currAction);
            switch (type) {
                case 'memberUser':
                    if(isAdd) {
                        that.formUser = {};
                    }
                    else {
                        entry = that.memberUsers[scopeIndex];
                        that.formUser = {
                            id: entry.id,
                            name: entry.name,
                            nickname: entry.nickname,
                            oldPassword: '',
                            password: '',
                            passwordOrgi: '',
                            typeId: entry.typeId,
                            isHeadhunting: entry.isHeadhunting,
                            memberId: entry.memberId,
                            status: entry.status,
                        };
                    }
                    that.dialogShow.memberUser = !that.dialogShow.memberUser;
                    break;
                case 'sysUser':
                    if(isAdd) {
                        that.formSysUser = {};
                    }
                    else {
                        entry = that.sysUsers[scopeIndex];
                        that.formSysUser = {
                            id: entry.id,
                            name: entry.name,
                            nickname: entry.nickname,
                            oldPassword: '',
                            password: '',
                            passwordOrgi: '',
                        };
                    }
                    that.dialogShow.sysUser = !that.dialogShow.sysUser;
                    break;
                case 'authModule':
                    that.getAllAuthModules();
                    if(isAdd) {
                        that.formAuthModule = {};
                    }
                    else {
                        entry = that.authModules[scopeIndex];
                        that.formAuthModule = {
                            moduleId: entry.moduleId,
                            parentId: entry.parentId,
                            moduleName: entry.moduleName,
                            moduleDesc: entry.moduleDesc,
                            moduleUrl: entry.moduleUrl,
                            isLeaf: entry.isLeaf,
                            fullModuleName: entry.fullModuleName,
                        };
                    }
                    that.dialogShow.authModule = !that.dialogShow.authModule;
                    break;
                case 'role':
                    if(isAdd) {
                        that.formAuthRole = {};
                    }
                    else {
                        entry =  that.roles[scopeIndex];
                        that.formAuthRole = {
                            roleId: entry.roleId,
                            roleName: entry.roleName,
                            roleDesc: entry.roleDesc
                        };
                    }

                    that.dialogShow.role = !that.dialogShow.role;
                    break;
                case 'categoryType':
                    if(isAdd) {
                        that.formCategoryType = {};
                    }
                    else {
                        entry = that.categoryTypes[scopeIndex];
                        console.log('entry',entry,that.categoryTypes);
                        that.formCategoryType = {
                            recordId: entry.recordId,
                            name: entry.name,
                            desc: entry.desc,
                        };
                    }
                    that.dialogShow.categoryType = !that.dialogShow.categoryType;
                    break;
                case 'category':
                    that.getAllCategoryTypes();
                    if(isAdd) {
                        that.formCategory = {};
                    }
                    else {
                        entry = that.categories[scopeIndex];
                        that.formCategory = {
                            recordId: entry.recordId,
                            typeId: entry.typeId,
                            typeName: entry.typeName,
                            name: entry.name,
                            desc: entry.desc
                        };
                    }
                    that.dialogShow.category = !that.dialogShow.category;
                    break;

                default: break;
            }
        },
        handleDel(idx,type) {
            let that = this;
            let entry = null, params = null;
            console.log('handleDel,',idx,type);
            that.$confirm('是否确认提交？', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                callback: action => {
                    if(action === "cancel"){
                        that.$message({
                            type: 'info',
                            message: "取消提交"
                        });
                    }else{
                        switch (type) {
                            case 'sysUser':
                                that.sysUsers.splice(idx,1);
                                break;
                            case 'memberUser':
                                that.memberUsers.splice(idx,1);
                                break;
                            case 'authModule':
                                let authModuleEntry = that.authModules[idx];
                                let moduleParams = new URLSearchParams();
                                moduleParams.append("id",authModuleEntry.moduleId);
                                moduleParams.append("flag",authModuleEntry.recordFlag);
                                axios.post("/api/user/updateModuleFlagById",moduleParams)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                            that.authModules.splice(idx,1);
                                            that.pager.authModule.currentPage = 1;
                                            that.loadAuthModules('',1,that.pager.authModule.pageSize);
                                            that.$message({
                                                message: '权限模块信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;
                            case 'role':
                                let authRoleEntry = that.roles[idx];
                                let roleParams = new URLSearchParams();
                                roleParams.append("id",authRoleEntry.id);
                                roleParams.append("flag",authRoleEntry.recordFlag);
                                axios.post("/api/user/updateRoleFlagById",roleParams)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                            that.roles.splice(idx,1);
                                            that.pager.role.currentPage = 1;
                                            that.loadAuthRoles('',1,that.pager.authRole.pageSize);
                                            that.$message({
                                                message: '角色信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;

                            case 'category':
                                entry = that.categories[idx];
                                params = new URLSearchParams();
                                params.append('id',entry.recordId);
                                params.append('flag','0');
                                axios.post("/api/category/updateFlagById",params)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                            that.categories.splice(idx,1);
                                            that.pager.category.currentPage = 1;
                                            that.loadCategories('','',1, that.pager.category.pageSize);
                                            that.$message({
                                                message: '类型信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                        else {
                                            that.$message({
                                                message: '类型信息删除失败!',
                                                type: 'error'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;
                            case 'categoryType':
                                entry = that.categoryTypes[idx];
                                params = new URLSearchParams();
                                params.append('id',entry.recordId);
                                params.append('flag','0');
                                axios.post("/api/category/updateTypeFlagById",params)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                            that.categoryTypes.splice(idx,1);
                                            that.pager.categoryType.currentPage = 1;
                                            that.loadCategoryTypes('', 1, that.pager.categoryType.pageSize);
                                            that.$message({
                                                message: '类型类别信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                        else {
                                            that.$message({
                                                message: '类型类别信息删除失败!',
                                                type: 'error'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;
                            case 'langConf':
                                entry = that.langConfs[idx];
                                params = new URLSearchParams();
                                params.append('id',entry.recordId);
                                params.append('flag','0');
                                axios.post("/api/title/updateLangFlagById",params)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                            that.langConfs.splice(idx,1);
                                            that.pager.langConf.currentPage = 1;
                                            that.loadLangConfs('', 1, that.pager.langConf.pageSize);
                                            that.$message({
                                                message: '语言配置信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                        else {
                                            that.$message({
                                                message: '语言配置信息删除失败!',
                                                type: 'error'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;
                            default: break;
                        }
                    }
                }
            });

        },

        /**
         * 角色-模块信息设置
         * @param role
         */
        handleSetRoleModule(role) {
            // console.log('role',role);
            let that = this;
            that.checkBoxRelationId.roleModule = role.roleId;
            that.checkBoxOptions.roleModule = [];
            axios.get("/api/user/findRoleModuleByRoleId",{params:{
                    roleId: role.roleId
                }})
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        let checkedEntries = data.data;
                        for(let j=0;j<checkedEntries.length;j++) {
                            that.checkBoxOptions.roleModule.push(checkedEntries[j].moduleId);
                        }
                        // console.log('that.checkBoxOptions.roleModule',that.checkBoxOptions.roleModule);
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });

            that.dialogShow.roleModule = true;
        },

        /**
         * 用户-角色信息设置
         * @param user 用户信息
         * @param isAdmin 是否为管理员
         */
        handleSetUserRole(user,isAdmin) {
            // console.log('user',user,'isAdmin:'+isAdmin);
            let that = this;
            if(isAdmin) {
                that.checkBoxRelationId.sysUserRole = user.userId;
                that.checkBoxOptions.sysUserRole = [];
            }
            else {
                that.checkBoxRelationId.userRole = user.userId;
                that.checkBoxOptions.userRole = [];
            }
            axios.get("/api/user/findUserRoleByUserId",{params:{
                    userId: user.userId
                }})
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        let checkedEntries = data.data;
                        for(let j=0;j<checkedEntries.length;j++) {
                            let checkedRoleId = checkedEntries[j].roleId;
                            if(isAdmin) {
                                that.checkBoxOptions.sysUserRole.push(checkedRoleId);
                                // console.log('that.checkBoxOptions.sysUserRole',that.checkBoxOptions.sysUserRole);
                            }
                            else {
                                that.checkBoxOptions.userRole.push(checkedRoleId);
                                // console.log('that.checkBoxOptions.userRole',that.checkBoxOptions.userRole);
                            }
                        }
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });

            that.dialogShow.userRole = true;
        },

        /**
         * 复选框选择保存
         * @param checkBoxType
         */
        handleCheckBoxSave(checkBoxType) {
            let that = this;
            if(checkBoxType && checkBoxType != undefined) {
                switch (checkBoxType) {
                    case 'roleModule':
                        // console.log(that.checkBoxOptions.roleModule);
                        if(that.checkBoxOptions.roleModule.length == 0) {
                            that.$message({
                                message: '请选择权限模块!',
                                type: 'warning'
                            });
                        }
                        else { //保存提交
                            let roleModuleParams = new URLSearchParams();
                            roleModuleParams.append('roleId',that.checkBoxRelationId.roleModule);
                            roleModuleParams.append('moduleIds',that.checkBoxOptions.roleModule);
                            axios.post("/api/user/saveRoleModule",roleModuleParams)
                                .then(function(response){
                                    if(parseInt(response.data.code) === 200){
                                        that.dialogShow.roleModule = false;
                                        that.$message({
                                            message: '设置成功!',
                                            type: 'success'
                                        });
                                    }
                                }).catch(function(err){
                                console.warn(err);
                            });
                        }
                        break;
                    case 'userRole':
                        // console.log(that.checkBoxOptions.userRole);
                        if(that.checkBoxOptions.userRole.length == 0) {
                            that.$message({
                                message: '请选择角色!',
                                type: 'warning'
                            });
                        }
                        else {  //保存提交
                            let userRoleParams = new URLSearchParams();
                            userRoleParams.append('userId',that.checkBoxRelationId.userRole);
                            userRoleParams.append('roleIds',that.checkBoxOptions.userRole);
                            axios.post("/api/user/saveUserRole",userRoleParams)
                                .then(function(response){
                                    if(parseInt(response.data.code) === 200){
                                        that.dialogShow.userRole = false;
                                        that.$message({
                                            message: '设置成功!',
                                            type: 'success'
                                        });
                                    }
                                }).catch(function(err){
                                console.warn(err);
                            });
                        }
                        break;
                    case 'sysUserRole':
                        // console.log(that.checkBoxOptions.sysUserRole);
                        if(that.checkBoxOptions.sysUserRole.length == 0) {
                            that.$message({
                                message: '请选择角色!',
                                type: 'warning'
                            });
                        }
                        else {  //保存提交
                            let sysUserRoleParams = new URLSearchParams();
                            sysUserRoleParams.append('userId',that.checkBoxRelationId.sysUserRole);
                            sysUserRoleParams.append('roleIds',that.checkBoxOptions.sysUserRole);
                            axios.post("/api/user/saveUserRole",sysUserRoleParams)
                                .then(function(response){
                                    if(parseInt(response.data.code) === 200){
                                        that.dialogShow.userRole = false;
                                        that.$message({
                                            message: '设置成功!',
                                            type: 'success'
                                        });
                                    }
                                }).catch(function(err){
                                console.warn(err);
                            });
                        }
                        break;
                }
            }
        },


        /**
         * 加载所有角色信息
         * @param that
         */
        getAllAuthRole() {
            let that = this;
            that.allRoles = [];
            axios.get("/api/user/getAllRoles")
                .then(function(response){/!*成功*!/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.allRoles = data.data;
                    }
                })
                .catch(function(err){/!*异常*!/
                    console.log(err);
                });
        },

        /**
         * 加载所有权限模块信息
         */
        getAllAuthModules() {
            let that = this;
            that.allModules = [];
            axios.get("/api/user/getModules")
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.allModules = data.data;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },

        /**
         * 获得所有分类类别信息
         */
        getAllCategoryTypes() {
            let that = this;
            that.allCategoryTypes = [];
            axios.get("/api/category/allCategoryTypes")
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.allCategoryTypes = data.data;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },

        /**
         * 获取所有语言配置信息
         */
        getAllLangConfs() {
            let that = this;
            that.allLangConfs = [];
            axios.get("/api/title/allLang")
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.allLangConfs = data.data;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },

        /**
         * 根据角色id获得权限模块
         * @param roleId
         */
        getAuthModuleByRoleId(roleId) {
            let that = this;
            that.roleModules = [];
            axios.get("/api/auth/findAuthModuleByRoleId",{params:{
                    roleId: roleId
                }})
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.roleModules = JSON.parse(data.data);
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },

        /**
         * 根据用户id获得角色信息
         * @param userId
         */
        getAuthRolesByUserId(userId) {
            let that = this;
            that.userRoles = [];
            axios.get("/api/auth/findAuthRolesByUserId",{params:{
                    userId: userId
                }})
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.userRoles = JSON.parse(data.data);
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },

        /**
         * 加载权限模块信息
         */
        loadAuthModules(criteria, pageNum, pageSize) {
            let that = this;
            axios.get("/api/user/searchModule",{params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize:pageSize,
                    parentId: that.formSearchAuthModule.parentId,
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.authModules = response.data.data.list;
                        that.pager.authModule.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleAuthModuleSizeChange: function(val) {
            this.pager.authModule.pageSize = val;
            this.loadAuthModules(this.pager.authModule.criteria, this.pager.authModule.currentPage, this.pager.authModule.pageSize);
        },

        //页码变更
        handleAuthModuleCurrentChange: function(val) {
            this.pager.authModule.currentPage = val;
            this.loadAuthModules(this.pager.authModule.criteria, this.pager.authModule.currentPage, this.pager.authModule.pageSize);
        },

        /**
         * 加载角色信息
         */
        loadAuthRoles(criteria, pageNum, pageSize) {
            let that = this;
            axios.get("/api/user/searchRole",{params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize:pageSize
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.roles = response.data.data.list;
                        that.pager.authRole.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleAuthRoleSizeChange: function(val) {
            this.pager.authRole.pageSize = val;
            this.loadAuthRoles(this.pager.authRole.criteria, this.pager.authRole.currentPage, this.pager.authRole.pageSize);
        },

        //页码变更
        handleAuthRoleCurrentChange: function(val) {
            this.pager.authRole.currentPage = val;
            this.loadAuthRoles(this.pager.authRole.criteria, this.pager.authRole.currentPage, this.pager.authRole.pageSize);
        },

        /**
         * 加载(会员)用户信息
         */
        loadMemberUsers(criteria,typeId,pageNum, pageSize) {
            let that = this;
            axios.get("/api/user/search",{params:{
                    userNickname: criteria,
                    userType: typeId,
                    pageNum: pageNum,
                    pageSize:pageSize
                }})
                .then(function(response){/!*成功*!/
                    if(parseInt(response.status) == 200 ) {
                        that.memberUsers = response.data.data.list;
                        that.pager.user.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/!*异常*!/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleMemberUserSizeChange: function(val) {
            this.pager.user.pageSize = val;
            this.loadMemberUsers(this.pager.user.criteria, this.pager.user.typeId, this.pager.user.currentPage, this.pager.user.pageSize);
        },

        //页码变更
        handleMemberUserCurrentChange: function(val) {
            this.pager.user.currentPage = val;
            this.loadMemberUsers(this.pager.user.criteria, this.pager.user.typeId, this.pager.user.currentPage, this.pager.user.pageSize);
        },

        /**
         * 加载(系统)用户信息
         */
        loadSysUsers(criteria, pageNum, pageSize) {
            let that = this;
            axios.get("/api/user/search",{params:{
                    userNickname: criteria,
                    pageNum: pageNum,
                    pageSize:pageSize,
                    userType:1
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.sysUsers = response.data.data.list;
                        that.pager.sysUser.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleSysUserSizeChange: function(val) {
            this.pager.sysUser.pageSize = val;
            this.loadSysUsers(this.pager.sysUser.criteria, this.pager.sysUser.currentPage, this.pager.sysUser.pageSize);
        },

        //页码变更
        handleSysUserCurrentChange: function(val) {
            this.pager.sysUser.currentPage = val;
            this.loadSysUsers(this.pager.sysUser.criteria, this.pager.sysUser.currentPage, this.pager.sysUser.pageSize);
        },

        /**
         * 角色-模块全选
         * @param val
         */
        handleRoleModuleCheckAllChange(val) {
            let that = this,checkIds = [];
            for(let i = 0;i < that.allModules.length; i ++ ) {
                let entry = that.allModules[i];
                checkIds.push(entry.id);
            }
            // console.log(that.checkBoxOptions.roleModule,that.allModules,val,checkIds);
            that.checkBoxOptions.roleModule = val ? checkIds : [];
            that.isIndeterminate.roleModule = false;
        },
        /**
         * 角色-模块单选
         * @param value
         */
        handleRoleModuleCheckedChange(value) {
            let that = this;
            let checkedCount = value.length;
            that.checkBoxAll.roleModule = (checkedCount === that.allModules.length);
            that.isIndeterminate.roleModule = checkedCount > 0 && checkedCount < that.allModules.length;
        },

        /**
         * (系统)用户-权限全选
         * @param val
         */
        handleSysUserRoleCheckAllChange(val) {
            let that = this,checkIds = [];
            for(let i = 0;i < that.allRoles.length; i ++ ) {
                let entry = that.allRoles[i];
                checkIds.push(entry.id);
            }
            that.checkBoxOptions.sysUserRole = val ? checkIds : [];
            that.isIndeterminate.sysUserRole = false;
        },
        /**
         * (系统)用户-权限单选
         * @param value
         */
        handleSysUserRoleCheckedChange(value) {
            let that = this;
            let checkedCount = value.length;
            that.checkBoxAll.sysUserRole = (checkedCount === that.allRoles.length);
            that.isIndeterminate.sysUserRole = checkedCount > 0 && checkedCount < that.allRoles.length;
        },

        /**
         * (会员)用户-权限全选
         * @param val
         */
        handleUserRoleCheckAllChange(val) {
            let that = this,checkIds = [];
            for(let i = 0;i < that.allRoles.length; i ++ ) {
                let entry = that.allRoles[i];
                if(entry.name != 'ROLE_USER' && entry.name != 'ROLE_ADMIN') {
                    checkIds.push(entry.id);
                }
            }
            that.checkBoxOptions.userRole = val ? checkIds : [];
            that.isIndeterminate.userRole = false;
        },
        /**
         * (会员)用户-权限单选
         * @param value
         */
        handleUserRoleCheckedChange(value) {
            let that = this,checkIds = [];
            for(let i = 0;i < that.allRoles.length; i ++ ) {
                let entry = that.allRoles[i];
                if(entry.name != 'ROLE_USER' && entry.name != 'ROLE_ADMIN') {
                    checkIds.push(entry.id);
                }
            }
            let checkedCount = value.length;
            that.checkBoxAll.userRole = (checkedCount === checkIds.length);
            that.isIndeterminate.userRole = checkedCount > 0 && checkedCount < checkIds.length;
        },

        /**
         * 加载分类信息
         */
        loadCategories(criteria, typeId, pageNum, pageSize) {
            let that = this;
            axios.get("/api/category/search",{params:{
                    key: criteria,
                    typeId: typeId,
                    pageNum: pageNum,
                    pageSize:pageSize
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.categories = response.data.data.list;
                        that.pager.category.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleCategorySizeChange: function(val) {
            this.pager.category.pageSize = val;
            this.loadCategories(this.pager.category.criteria, this.formSearchCategory.typeId, this.pager.category.currentPage, this.pager.category.pageSize);
        },

        //页码变更
        handleCategoryCurrentChange: function(val) {
            this.pager.category.currentPage = val;
            this.loadCategories(this.pager.category.criteria, this.formSearchCategory.typeId, this.pager.category.currentPage, this.pager.category.pageSize);
        },

        /**
         * 加载分类类别信息
         */
        loadCategoryTypes(criteria, pageNum, pageSize) {
            let that = this;
            axios.get("/api/category/searchType",{params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize:pageSize
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.categoryTypes = response.data.data.list;
                        that.pager.categoryType.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleCategoryTypeSizeChange: function(val) {
            this.pager.categoryType.pageSize = val;
            this.loadCategoryTypes(this.pager.categoryType.criteria, this.pager.categoryType.currentPage, this.pager.categoryType.pageSize);
        },

        //页码变更
        handleCategoryTypeCurrentChange: function(val) {
            this.pager.categoryType.currentPage = val;
            this.loadCategoryTypes(this.pager.categoryType.criteria, this.pager.categoryType.currentPage, this.pager.categoryType.pageSize);
        },

        /**
         * 加载语言配置信息
         */
        loadLangConfs(criteria, pageNum, pageSize) {
            let that = this;
            axios.get("/api/title/searchLang",{params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize:pageSize
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.langConfs = response.data.data.list;
                        that.pager.langConf.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleLangConfSizeChange: function(val) {
            this.pager.langConf.pageSize = val;
            this.loadLangConfs(this.pager.langConf.criteria, this.pager.langConf.currentPage, this.pager.langConf.pageSize);
        },

        //页码变更
        handleLangConfCurrentChange: function(val) {
            this.pager.langConf.currentPage = val;
            this.loadLangConfs(this.pager.langConf.criteria, this.pager.langConf.currentPage, this.pager.langConf.pageSize);
        },

    },
    props: {

    },
    beforeCreate: function() {
        let that = this;
        axios.defaults.withCredentials = true;
        axios.get("/api/admin/main/struct")
            .then(function(response){/*成功*/
                let config = response.data.formStructConfig;
                that.formUser = config.formUser;
                that.formSysUser = config.formSysUser;
                that.formUserType = config.formUserType;
                that.formUserDetail = config.formUserDetail;
                that.formMember = config.formMember;
                that.formOperateLog = config.formOperateLog;
                that.formAuthUserRole = config.formAuthUserRole;
                that.formAuthRoleModule = config.formAuthRoleModule;
                that.formAuthRole = config.formAuthRole;
                that.formAuthModule = config.formAuthModule;
                that.formCategory = config.formCategory;
                that.formCategoryType = config.formCategoryType;
                that.formDict = config.formDict;
                that.formLangConf = config.formLangConf;

                that.dialogShow = config.dialogShow;
                that.rules = config.rules;
              //  console.log('rules,',that.rules);
                let searchForm = config.searchForm;
                that.formSearchAuthModule = searchForm.authModule;
                that.formSearchArticle = searchForm.article;
                that.formSearchCategory = searchForm.category;
                that.formSearchCategoryType = searchForm.categoryType;
                that.formSearchDict = searchForm.dict;
                that.formSearchLangConf = searchForm.langConf;
                that.formSearchMemberUser = searchForm.memberUser;
            })
            .catch(function(err){/*异常*/
                console.log(err);
            });
    },
    created: function () {
        //this.loadArticleTypes();
    },
    mounted: function () {
        let that = this;
        that.getAllAuthRole();
        that.loadMemberUsers('','',0, that.pager.user.pageSize);
        that.ueditors.article = UE.getEditor('articleEditor', that.ueditorConfig);
        that.ueditors.article.addListener("ready", function () {
            that.ueditors.article.setContent('你好nihao ', false); // 确保UE加载完成后，放入内容。
        });

        /*
        that.ueditors.jobCompany = UE.getEditor('jobCompanyEditor',that.ueditorConfig);
        that.ueditors.jobCompany.addListener("ready", function () {
            that.ueditors.jobCompany.setContent('你好nihao ', false); // 确保UE加载完成后，放入内容。
        });
         */
    },
    destroyed: function() {
        this.ueditors.article.destroy();
    //    this.ueditors.jobCompany.destroy();
    }
})
