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
                case 'dwjbxx':
                	that.getUpperOrg();
                	that.getTreeDict();
                	that.loadDwjbxx();
                	break;
                case 'announce':
                    that.getAllMsgCategories();
                    that.loadNews('', 1, that.pager.news.pageSize);
                    break;
                case 'articles':
                    that.getArticleCategories();
                    that.loadArticles('',1, that.pager.article.pageSize);
                    that.currAction = 'append';
                    that.def_menu_id = 'articles';
                    break;
                case 'formArticle':
                    that.getArticleCategories();
                    if(that.currAction == 'edit') {
                        setTimeout(function() {
                            that.ueditors.article.setContent(that.formArticle.content, false);
                        }, 500);

                        that.currAction = 'append';
                        that.def_menu_id = 'formArticle';
                    }
                    else {
                        that.currAction = 'append';
                        setTimeout(function() {
                            that.ueditors.article.setContent('', false);
                        }, 500);
                        that.formArticle = {};
                    }
                    break;
                case 'nddyxxcj':
                	that.setDyxxYear();
                	break;

                case 'ndsjdfqk':
                    that.loadPartyDues('', 1, that.pager.partyDues.pageSize);
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
        dwjbxxTableData: [],
        upperOrg:[],
        currentUser: {},        // 当前用户信息
        userOwnedModules: [],    //  当前用户所拥有(的所有角色)的模块
        userOwnedMenus: [],     // 当前用户所拥有(的所有角色)的菜单
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
        formNews: {},
        formArticle: {},
        formPartyDues: {},
        loading:{},
        dyxxyear:{
        },
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
        formSearchNews: {},
        formSearchArticle: {},
        formSearchPartyDues: {},
        formdwjbxx:{},
        formLeader:{},
        formDept:{},
        dialogShow: {},
        memberUsers: [],
        sysUsers: [],
        orgType:[],
        orgAddressRelation:[],
        elctType:[],
        leadTime:[],
        changeOrgRelationAuth:[],
        isDelPartPersonAuth:[],
        belongArea:[],
        formReward:[],
        authModules: [],
        deptOrgType:[],
        leaderList:[
        ],
        articles: [],
        articleTypes: [],
        
        ssdzzqk:[
        	{
        		orgId: '1231231231',
        		orgName: '创新事业部1',
        		upperOrgName: '博彦泓智',
        		upperOrgAttr: 'it外包',
        		orgAttr: '外包',
        		contactPersion: 'skfjl',
        		phone: '7151551',
        		count: '10'
        	},
        	{
        		orgId: '1231231232',
        		orgName: '创新事业部2',
        		upperOrgName: '博彦泓智',
        		upperOrgAttr: 'it外包',
        		orgAttr: '外包',
        		contactPersion: 'skfjl',
        		phone: '7151551',
        		count: '10'
        	},
        	{
        		orgId: '1231231233',
        		orgName: '创新事业部3',
        		upperOrgName: '博彦泓智',
        		upperOrgAttr: 'it外包',
        		orgAttr: '外包',
        		contactPersion: 'skfjl',
        		phone: '7151551',
        		count: '10'
        	},
        	{
        		orgId: '1231231234',
        		orgName: '创新事业部4',
        		upperOrgName: '博彦泓智',
        		upperOrgAttr: 'it外包',
        		orgAttr: '外包',
        		contactPersion: 'skfjl',
        		phone: '7151551',
        		count: '10'
        	},
        	{
        		orgId: '1231231235',
        		orgName: '创新事业部5',
        		upperOrgName: '博彦泓智',
        		upperOrgAttr: 'it外包',
        		orgAttr: '外包',
        		contactPersion: 'skfjl',
        		phone: '7151551',
        		count: '10'
        	}
        ],
        allRoles: [],
        allModules: [],
        articleCategories: [],
        allInsectCategories: [],
        allCategoryTypes: [],
        allMsgCategories: [],
        allLangConfs: [],
        allTitleTypes: [],
        allLangAndType: {},
        allTitleConfs: {},
        roleModules: [],
        userRoles: [],
        roles: [],
        dwjbxxTreeLevel:{},
        deptInfoList:[],
        rewardList:[],
        categories: [],
        categoryTypes : [],
        dwjbxxDialog:{},
        langConfs: [],
        newsArray: [],
        partyDuesArray: [],
        newsReceiveUsers: {
            '1fe30445-96ec-4a1d-88e2-749f29440bef2': {
                userId: '1fe30445-96ec-4a1d-88e2-749f29440bef2',
                userName: '蔡',
                userNickname: '传龙',
            },
            '1fe30445-96ec-4a1d-88e2-749f29440bef3': {
                userId: '1fe30445-96ec-4a1d-88e2-749f29440bef3',
                userName: '魏',
                userNickname: '懊悔',
            },
            '1fe30445-96ec-4a1d-88e2-749f29440bef4': {
                userId: '1fe30445-96ec-4a1d-88e2-749f29440bef4',
                userName: '吕',
                userNickname: '红娟',
            },
        },   // 消息接收人(用于'选择接收人'dialog)
        newsReceiveUserIds: [],
        currNewsSendRecord: {},
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
            news: {
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
            article: {
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

            partyDues: {
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
            let that = this;
            that.$refs[formName].resetFields();
            if(that.showContent === 'formArticle') {
                setTimeout(function() {
                    that.ueditors.article.setContent("", false);
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
                            	that.$refs[formName].validate((valid) => {
                            	    console.log('valid', valid);
                            	    if (valid) {
                            	      // 表单验证通过之后的操作
                            	    	switch (formName) {
	                                        case 'formAuthRole' : that.sumbitAuthRole(); break;
                                            case 'formArticle': that.submitArticle(); break;
	                                        case 'formAuthModule': that.submitAuthModule(); break;
	                                        case 'formUser': that.submitUser(false); break;
	                                        case 'formSysUser': that.submitUser(true); break;
	
	                                        case 'formCategoryType': that.submitCategoryType(); break;
	                                        case 'formCategory': that.submitCategory(); break;
	
	                                        case 'formLangConf': that.submitLangConf(); break;
	                                        case 'formdwjbxx': 
	                                        	that.submitDwjbxx();
	                                        	
	                                        	break;
                                            case 'formNews': that.submitNews(); break;
                                            case 'formPartyDues': that.submitPartyDues(); break;
	                                        default: break;
	                                    }
	                                    //提交成功之后
	                                    if(formName != 'formdwjbxx'){
	                                    	that.resetForm(formName);
	                                    }
                            	    } else {
                            	    	
                            	    	this.$message.error('提交失败,请按要求填写表单内容');
                            	      return false;
                            	    }
                            	  });
                                
                            }
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
                case 'searchDwjbxx':
                	that.loadDwjbxx();
                	break;
                case 'formSearchNews':
                    that.loadNews(that.formSearchNews.title, 1, that.pager.news.pageSize);
                    break;
                case 'formSearchArticle':
                    that.loadArticles(that.formSearchArticle.title, 1, that.pager.article.pageSize);
                    break;
                case 'formSearchPartyDues':
                    that.loadPartyDues(that.formSearchPartyDues.key, 1, that.pager.partyDues.pageSize);
                    break;
            }
        },
        setDyxxYear(){
        	let that = this;
        	let date = new Date();
        	let currentYear = date.getFullYear();
        	that.dyxxyear.year = currentYear;
        	that.dyxxyear.index1 = currentYear;
        	that.dyxxyear.index2 = currentYear-1;
        	that.dyxxyear.index3 = currentYear-2;
        	that.dyxxyear.index4 = currentYear-3;
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
        copyCode(){
    		var orgCodeDom = document.getElementById("orgCode");
    		orgCodeDom.select();
    		document.execCommand("Copy");
    		this.$message({
    	          message: '党组织代码复制成功',
    	          type: 'success'
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
            params.append('moduleCode', that.formAuthModule.moduleCode || '');
            params.append('order', that.formAuthModule.order || 0);
            params.append('moduleIcon', that.formAuthModule.moduleIcon || '');
            params.append('moduleStyle', that.formAuthModule.moduleStyle || '');
            params.append('isMenu', that.formAuthModule.isMenu || 0);
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
         * 文章提交
         */
        submitArticle() {
            let that = this;
            let params = new URLSearchParams();
            let operName = '添加';
            params.append('typeId',that.formArticle.typeId || '');
            params.append('categoryId',that.formArticle.categoryId || '');
            params.append('title', that.formArticle.title || '');
            params.append('intro',that.formArticle.intro || '');
            params.append('content',that.ueditors.article.getContent() || '');
            params.append('tags',that.formArticle.tags || '');
            params.append('authorName',that.formArticle.authorName || '');
            params.append('source',that.formArticle.source || '');
            params.append('sourceSite',that.formArticle.sourceSite || '');
            let articleRecordId = that.formArticle.recordId || '';
            // 由于文章的提交操作比较特殊，故不能使用that.currAction 来判定是否为'修改'操作
            /*
            if(that.currAction === 'edit') {
                operName = '修改';
            }
            */
            if(articleRecordId != '') {
                operName = '修改';
                params.append('recordId', articleRecordId);
            }

            axios.post("/api/article/save",params)
                .then(function(response){
                    that.responseMessageHandler(response, '文章信息', operName, function() {
                        that.dialogShow.article = false;
                        that.pager.article.currentPage = 1;
                        that.loadArticles('',1,that.pager.article.pageSize);
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
         * 党委信息提交
         */
        submitDwjbxx(){
        	let that = this;
        	let params = new URLSearchParams();
        	that.loading.flag = true;
        	if(that.formdwjbxx.orgId != null){
            	params.append('orgId',that.formdwjbxx.orgId);
        	}
        	if(that.formdwjbxx.orgName != null){
        		params.append('orgName',that.formdwjbxx.orgName);
        	}
        	if(that.formdwjbxx.phone != null){
        		params.append('phone',that.formdwjbxx.phone);
        	}
        	if(that.formdwjbxx.upperOrg != null){
        		params.append('upperOrg',that.formdwjbxx.upperOrg);
        	}
        	if(that.formdwjbxx.foundTime != null){
        		params.append('foundTime',that.formdwjbxx.foundTime);
        	}
        	if(that.formdwjbxx.transCode != null){
        		params.append('transCode',that.formdwjbxx.transCode);
        	}
        	if(that.formdwjbxx.fixPhone != null){
        		params.append('fixPhone',that.formdwjbxx.fixPhone);
        	}
        	if(that.formdwjbxx.address != null){
        		params.append('address',that.formdwjbxx.address);
        	}
        	if(that.formdwjbxx.orgType != null){
        		params.append('orgType',that.formdwjbxx.orgType);
        	}
        	if(that.formdwjbxx.orgCode != null){
        		params.append('orgCode',that.formdwjbxx.orgCode);
        	}
        	if(that.formdwjbxx.orgAddressRelation != null){
        		params.append('orgAddressRelation',that.formdwjbxx.orgAddressRelation);
        	}
        	if(that.formdwjbxx.elctType != null){
        		params.append('elctType',that.formdwjbxx.elctType);
        	}
        	if(that.formdwjbxx.leadTime != null){
        		params.append('leadTime',that.formdwjbxx.leadTime);
        	}
        	if(that.formdwjbxx.currentLeaderTime != null){
        		params.append('currentLeaderTime',that.formdwjbxx.currentLeaderTime);
        	}
        	if(that.formdwjbxx.currentLeadOutTime != null){
        		params.append('currentLeadOutTime',that.formdwjbxx.currentLeadOutTime);
        	}
        	if(that.formdwjbxx.elctShoudPeopleCount != null){
        		params.append('elctShoudPeopleCount',that.formdwjbxx.elctShoudPeopleCount);
        	}
        	if(that.formdwjbxx.elctActPeopleCount != null){
        		params.append('elctActPeopleCount',that.formdwjbxx.elctActPeopleCount);
        	}
        	if(that.formdwjbxx.upperSureOrgCount != null){
        		params.append('upperSureOrgCount',that.formdwjbxx.upperSureOrgCount);
        	}
        	if(that.formdwjbxx.actUpperOrgPerCount != null){
        		params.append('actUpperOrgPerCount',that.formdwjbxx.actUpperOrgPerCount);
        	}
        	if(that.formdwjbxx.changeOrgRelationAuth != null){
        		params.append('changeOrgRelationAuth',that.formdwjbxx.changeOrgRelationAuth);
        	}
        	if(that.formdwjbxx.isDelPartPersonAuth != null){
        		params.append('isDelPartPersonAuth',that.formdwjbxx.isDelPartPersonAuth);
        	}
        	if(that.formdwjbxx.concatPersion != null){
        		params.append('concatPersion',that.formdwjbxx.concatPersion);
        	}
        	if(that.formdwjbxx.orgJobPhone != null){
        		params.append('orgJobPhone',that.formdwjbxx.orgJobPhone);
        	}
        	if(that.formdwjbxx.belongArea != null){
        		params.append('belongArea',that.formdwjbxx.belongArea);
        	}
        	params.append('leaderDetails',JSON.stringify(that.leaderList));
        	params.append('rewardDetails',JSON.stringify(that.rewardList));
        	params.append('deptDetails',JSON.stringify(that.deptInfoList));
//        	params.append('leaderDetails',that.leaderList);
//        	params.append('rewardDetails',that.rewardList);
//        	params.append('deptDetails',that.deptInfoList);
        	axios.post("/api/org/orgOpreate",params)
        		.then(function(response){
        			console.log(response)
        			if(parseInt(response.data.code) === 200){
        				that.loading.flag = false;
        				that.dialogShow.dwjbxx =false;
            			that.formdwjbxx={};
            			that.getUpperOrg();
            			that.loadDwjbxx();
                        that.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                    }else{
                    	that.loading.flag = false;
                    	this.$message.error("提交失败");
                    }
        			
        		})
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

        /**
         * 消息提交
         */
        submitNews() {
            let that = this;
            console.log('submitNews', that.formNews);
            that.formNews.receiverId = that.formNews.receiverId || '';
            let _len = that.formNews.receiveUsers.length;
            if (_len == 0) {
                that.$message.error('请选择接收人');
                return;
            }
            else {
                for (let i = 0; i < _len; i ++) {
                    if (that.formNews.receiverId == '') {
                        that.formNews.receiverId = that.formNews.receiveUsers[i].userId;
                    }
                    else {
                        that.formNews.receiverId += ',' +  that.formNews.receiveUsers[i].userId;
                    }
                }
            }
            console.log('submitNews', that.formNews);
            let params = new URLSearchParams();
            let operName = '新增';
            /*
            params.append('receiverId', that.formNews.receiverId);
            params.append('title',that.formNews.title);
            params.append('content',that.formNews.content);
            params.append('sendSms', that.formNews.sendSms ? '1' : '0');
            params.append('sendMail', that.formNews.sendMail ? "1" : "0");
             */
            let _data = {
                "recordId" : that.formNews.recordId || '',
                "receiverId" : that.formNews.receiverId,
                "title" : that.formNews.title,
                "content" : that.formNews.content,
                "sendSms" : that.formNews.sendSms ? '1' : '0',
                "sendMail" : that.formNews.sendMail ? "1" : "0",
                "tags" : that.formNews.tags || '',
                "remark" : that.formNews.remark || '',
                "typeId" : that.formNews.typeId || 'msg-notice',
                "isReceipt" : that.formNews.isReceipt || '0',
                "startTime" : that.formNews.startTime || '',
                "endTime" : that.formNews.endTime || '',
                "receiverType" : that.formNews.receiverType || '0',
                "recordFlag": that.formNews.recordFlag || '1',
            };
            if(that.currAction === 'edit') {
                operName = '修改';
                // params.append('recordId',that.formNews.recordId || '');
            }
            axios.post("/api/news/save", _data, {
                headers: {
                    "Content-type": "application/json;charset=utf-8"
                }
                })
                .then(function(response){
                    that.responseMessageHandler(response, '消息', operName, function() {
                        that.dialogShow.news = false;
                        that.pager.news.currentPage = 1;
                        that.loadNews('',1, that.pager.news.pageSize);
                    });
                }).catch(function(err){
                console.warn(err);
            });
        },

        /**
         * 党费缴纳提交
         */
        submitPartyDues() {
            let that = this;
            let params = new URLSearchParams();
            let operName = '添加';
            params.append('payTime', that.formPartyDues.payTime);
            params.append('payAmount',that.formPartyDues.payAmount || '0.00');
            params.append('remark',that.formPartyDues.remark || '');
            params.append('payPeriod',that.formPartyDues.payPeriod || '');
            if(that.currAction === 'edit') {
                operName = '修改';
                params.append('recordId',that.formPartyDues.recordId || '');
            }
            axios.post("/api/dues/save",params)
                .then(function(response){
                    that.responseMessageHandler(response, '党费缴纳', operName, function() {
                        that.dialogShow.partyDues = false;
                        that.pager.partyDues.currentPage = 1;
                        that.loadPartyDues('',1,that.pager.partyDues.pageSize);
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
        dwjgxxEdit(type,scopeIndex, scopeRow){
        	let that = this;
        	switch (type) {
        		case 'reward':
        			entry = that.rewardList[scopeIndex];
        			that.formReward = {
        					rewardName: entry.rewardName,
        					allowOrg: entry.allowOrg,
        					allowTime: entry.allowTime,
        					index: scopeIndex
        			}
        			that.dialogShow.reward = !that.dialogShow.reward;
        			break;
        		case 'leader':
        			entry = that.leaderList[scopeIndex];
        			that.formLeader = {
        					userName: entry.userName,
        					positon: entry.positon,
        					allowLeaderTime: entry.allowLeaderTime,
        					index: scopeIndex
        			}
        			that.dialogShow.leader = !that.dialogShow.leader;
        			break;
        		case 'dept':
        			entry = that.deptInfoList[scopeIndex];
        			that.formDept = {
        					deptName: entry.deptName,
        					deptNatureType: entry.deptNatureType,
        					deptType: entry.deptType,
        					index: scopeIndex
        			}
        			that.dialogShow.dept = !that.dialogShow.dept;
        			break;
        	}
        },
        formTaleConfirm(type){
        	let that = this;
        	
        	switch (type) {
        		case 'leader':
        			console.log(that.formLeader);
        			let rowValue = that.formLeader;
        			let scopeIndex = that.formLeader.index;
        			that.leaderList[scopeIndex] =rowValue;
        			let tmpleaderList = that.leaderList;
        			that.leaderList = [];
        			for(let i = 0 ; i < tmpleaderList.length ; i++){
        				that.leaderList.push(tmpleaderList[i]);
        			}
        			
        			
        			that.dialogShow.leader =false;
        			break;
        		case 'reward':
        			let rowValue1 = that.formReward;
        			let scopeIndex1 = that.formReward.index;
        			that.rewardList[scopeIndex1] =rowValue1;
        			let tmprewardList = that.rewardList;
        			that.rewardList = [];
        			for(let i = 0 ; i < tmprewardList.length ; i++){
        				that.rewardList.push(tmprewardList[i]);
        			}
        			
        			
        			that.dialogShow.reward =false;
        			break;
        		case 'dept':
        			let rowValue2 = that.formDept;
        			let scopeIndex2 = that.formDept.index;
        			that.deptInfoList[scopeIndex2] =rowValue2;
        			let tmpdeptInfoList = that.deptInfoList;
        			that.deptInfoList = [];
        			for(let i = 0 ; i < tmpdeptInfoList.length ; i++){
        				that.deptInfoList.push(tmpdeptInfoList[i]);
        			}
        			
        			
        			that.dialogShow.dept =false;
        			break;
        	}
        },
        edit(type,scopeIndex, scopeRow, isAdd) {
        	if(isAdd == null && type == 'dwjbxx'){
        		let that = this
        		that.formdwjbxx={
        				upperOrg: scopeRow.orgId
        		};
        		that.dwjbxxDialog={
        				title:scopeRow.orgName + '下级组织添加'
        		};
        		that.leaderList = [];
        		that.rewardList = [];
        		that.deptInfoList = [];
        		that.getOptionDict();
        		that.dialogShow.dwjbxx = !that.dialogShow.dwjbxx;
        	}else{
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
	                            isLeaf: entry.isLeaf + '',
	                            fullModuleName: entry.fullModuleName,
                                moduleCode: entry.moduleCode || '',
                                order: entry.order || 0,
                                moduleIcon: entry.moduleIcon || '',
                                moduleStyle: entry.moduleStyle || '',
                                isMenu: entry.isMenu + '' || '0',
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
	                case 'dwjbxx':
	                	//党委基本信息新增修改
	                	that.getOptionDict();
	                	if(isAdd){
	                		that.formdwjbxx={};
	                		that.dwjbxxDialog={
	                				title: '党委基本信息新增(根层党委)'
	                		};
	                		that.leaderList = [];
	                		that.rewardList = [];
	                		that.deptInfoList = [];
	                	}else{
	                		that.dwjbxxDialog={
	                				title: scopeRow.orgName
	                		};
	                		axios.get("/api/org/getOrgDetailById",{params:{
	                			orgId: scopeRow.orgId
	                        }})
	                        .then(function(response){/*成功*/
	                            let data = response.data;
	                            if(parseInt(data.code) === 200) {
	                            	that.formdwjbxx = response.data.result
	                            }
	                        })
	                        .catch(function(err){/*异常*/
	                            console.log(err);
	                        });
	                		//领导班子
	                		axios.get("/api/org/getOrgLeaderList",{params:{
	                			orgId: scopeRow.orgId
	                        }})
	                        .then(function(response){/*成功*/
	                            let data = response.data;
	                            if(parseInt(data.code) === 200) {
	                            	that.leaderList = response.data.result
	                            }
	                        })
	                        .catch(function(err){/*异常*/
	                        });
	                		
	                		
	                		//奖惩情况
	                		axios.get("/api/org/getOrgRewardList",{params:{
	                			orgId: scopeRow.orgId
	                        }})
	                        .then(function(response){/*成功*/
	                            let data = response.data;
	                            if(parseInt(data.code) === 200) {
	                            	that.rewardList = response.data.result
	                            }
	                        })
	                        .catch(function(err){/*异常*/
	                        });
	                		
	                		//单位信息
	                		axios.get("/api/org/getOrgDeptList",{params:{
	                			orgId: scopeRow.orgId
	                        }})
	                        .then(function(response){/*成功*/
	                            let data = response.data;
	                            if(parseInt(data.code) === 200) {
	                            	that.deptInfoList = response.data.result
	                            }
	                        })
	                        .catch(function(err){/*异常*/
	                        });
	                	}
	                	that.dialogShow.dwjbxx = !that.dialogShow.dwjbxx;
	                	break;
                    case 'news':
                        that.getAllMsgCategories();
                        if(isAdd) {
                            that.formNews = {
                            };
                        }
                        else {
                            entry = that.newsArray[scopeIndex];
                            that.formNews = {
                                recordId: entry.recordId,
                                title: entry.title || '',
                                content: entry.content || '',
                                tags: entry.tags || '',
                                remark: entry.remark || '',
                                typeId: entry.typeId || '',
                                isReceipt: entry.isReceipt + '' || '',
                                startTime: entry.startTime,
                                endTime: entry.endTime,
                                receiverType: entry.receiverType + '',
                                receiverId: entry.receiverId,
                                recordFlag: entry.recordFlag,
                                typeName: entry.typeName || '',
                                receiveRoles: entry.receiveRoles || [],
                                receiveUsers: entry.receiveUsers || [],
                                receiveUserIds: entry.receiveUserIds || [],
                                sendSms: (entry.sendSms == 1 || entry.sendSms == '1') ? true : false,
                                sendMail: (entry.sendMail == 1 || entry.sendMail == '1') ? true : false,
                            };
                        }
                        that.dialogShow.news = !that.dialogShow.news;
                        break;
                    case 'article':
                        if(isAdd) {
                            //that.def_menu_id = 'formArticle';
                            that.formArticle = {isEdit : false,};
                            setTimeout(function() {
                                that.ueditors.article.setContent('', false);
                            }, 500);
                            that.showContent = 'formArticle';
                        }
                        else {
                            //that.def_menu_id = 'formArticle';
                            entry = that.articles[scopeIndex];
                            that.formArticle = {
                                isEdit: true,
                                recordId: entry.recordId,
                                categoryId: entry.categoryId,
                                categoryName: entry.categoryName,
                                title: entry.title,
                                intro: entry.intro,
                                content: entry.content,
                                tags: entry.tags,
                                source: entry.source,
                                authorName: entry.authorName,
                                sourceSite: entry.sourceSite,
                                creatorId: entry.creatorId,
                                creatorName: entry.creatorName,
                                updatorId: entry.updatorId,
                                updatorName: entry.updatorName,
                                updateTime: entry.updateTime,
                                recordTime: entry.recordTime,
                                commentsCount: entry.commentsCount,
                                viewCount: entry.viewCount,
                                likeCount: entry.likeCount,
                                stinkyEgg: entry.stinkyEgg,
                            };
                            setTimeout(function() {
                                that.ueditors.article.setContent(entry.content, false);
                            }, 500);
                            that.showContent = 'formArticle';
                        }
                        //that.dialogShow.article = !that.dialogShow.article;
                        break;
                    case 'partyDues':
                        if(isAdd) {
                            that.formPartyDues = {
                            };
                        }
                        else {
                            entry = that.partyDuesArray[scopeIndex];
                            that.formPartyDues = {
                                recordId: entry.recordId,
                                userId: entry.userId,
                                payTime: entry.payTime,
                                payAmount: entry.payAmount || '0.00',
                                remark: entry.remark || '',
                                recordTime: entry.recordTime,
                                recordFlag: entry.recordFlag,
                                payPeriod: entry.payPeriod,
                                userName: entry.userName,
                                userNickname: entry.userNickname,
                                userSex: entry.userSex,
                                userOrgName: entry.userOrgName,
                            };
                        }
                        that.dialogShow.partyDues = !that.dialogShow.partyDues;
                        break;
	                default: break;
	            }
        	}
        },
        dwjbxxDel(row,type){
        	let that = this;
            let entry = null, params = null;
            console.log('handleDel,',row,type);
            that.$confirm('是否确认删除？', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                callback: action => {
                    if(action === "cancel"){
                        that.$message({
                            type: 'info',
                            message: "取消删除"
                        });
                    }else{
                        switch (type) {
                            
                            case 'dwjbxx':
                                let orgId = row.orgId;
                                let params = new URLSearchParams();
                                params.append("orgId",orgId);
                                axios.post("/api/org/delOrg",params)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                        	that.loadDwjbxx();
                                            that.$message({
                                                message: '删除成功',
                                                type: 'success'
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
                                moduleParams.append("id", authModuleEntry.moduleId);
                                moduleParams.append("flag", '0');
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
                                roleParams.append("id", authRoleEntry.id);
                                roleParams.append("flag", '0');
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
                            case 'news':
                                entry = that.newsArray[idx];
                                params = new URLSearchParams();
                                params.append('id', entry.recordId);
                                params.append('flag','0');
                                axios.post("/api/news/updateFlagById",params)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                            that.newsArray.splice(idx,1);
                                            that.pager.news.currentPage = 1;
                                            that.loadNews('', 1, that.pager.news.pageSize);
                                            that.$message({
                                                message: '消息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                        else {
                                            that.$message({
                                                message: '消息删除失败!',
                                                type: 'error'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;
                            case 'article':
                                entry = that.articles[idx];
                                params = new URLSearchParams();
                                params.append('id', entry.recordId);
                                params.append('flag','0');
                                axios.post("/api/article/updateArticleFlagById",params)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                            that.articles.splice(idx,1);
                                            that.pager.article.currentPage = 1;
                                            that.loadArticles('', 1, that.pager.article.pageSize);
                                            that.$message({
                                                message: '文章信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                        else {
                                            that.$message({
                                                message: '文章信息删除失败!',
                                                type: 'error'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;
                            case 'partyDues':
                                entry = that.partyDuesArray[idx];
                                params = new URLSearchParams();
                                params.append('id', entry.recordId);
                                params.append('flag','0');
                                axios.post("/api/dues/deleteById",params)
                                    .then(function(response){
                                        if(parseInt(response.data.code) === 200){
                                            that.partyDuesArray.splice(idx,1);
                                            that.pager.partyDues.currentPage = 1;
                                            that.loadPartyDues('', 1, that.pager.partyDues.pageSize);
                                            that.$message({
                                                message: '党费缴纳信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                        else {
                                            that.$message({
                                                message: '党费缴纳信息删除失败!',
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
         * 获得所有分类信息
         */
        getArticleCategories() {
            let that = this;
            that.articleCategories = [];
            axios.get("/api/category/allArticleCategories")
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.articleCategories = data.data;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },

        /**
         * 获得当前用户信息
         */
        getCurrentUserInfo() {
            let that = this;
            that.currentUser = {};
            that.userOwnedModules = [];
            that.userOwnedMenus = [];

            axios.get("/api/auth/getCurrentUser")
                .then(function(response){/*成功*/
                    let data = response.data;
                    console.log('getCurrentUserInfo=>', data)
                    that.currentUser = data.data;
                    let __modules = data.data['modules'] || [];
                    that.userOwnedModules = __modules;
                    let moduleLen = __modules.length;
                    for (let i = 0; i < moduleLen; i ++) {
                        let __module = __modules[i];
                        let __moduleId = __module.moduleId;
                        if (__module.isMenu == 1) {
                            if (__module.parentId == '' || __module.parentId == null) {
                                let __subMenus = [];
                                for (let j = 0; j < moduleLen; j++) {
                                    let __module2 = __modules[j];
                                    if (__module2.isMenu == 1 && __module2.parentId == __moduleId) {
                                        __subMenus.push(__module2);
                                    }
                                }
                                that.userOwnedMenus.push({
                                    moduleId: __moduleId,
                                    parentId: __module.parentId,
                                    moduleName: __module.moduleName,
                                    moduleDesc: __module.moduleDesc,
                                    moduleUrl: __module.moduleUrl,
                                    isLeaf: __module.isLeaf,
                                    fullModuleName: __module.fullModuleName,
                                    moduleCode: __module.moduleCode,
                                    order: __module.order,
                                    moduleIcon: __module.moduleIcon,
                                    moduleStyle: __module.moduleStyle,
                                    isMenu: __module.isMenu,
                                    subs: __subMenus,
                                });
                            }
                        }
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
         * 获得消息分类信息
         */
        getAllMsgCategories() {
            let that = this;
            that.allMsgCategories = [];
            axios.get("/api/category/allMsgCategories")
                .then(function(response){/*成功*/
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.allMsgCategories = data.data;
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
                    isMenu: that.formSearchAuthModule.isMenu,
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
         * 增加表行
         */
        addLine(tableName){
        	let rowValue={};
        	switch (tableName){
        	
        		case 'dept':
        			rowValue={
        				deptName:'',
        				deptNatureType:'',
        				deptType:''
        			}
        			//添加新的行数
                    this.deptInfoList.push(rowValue);
        			break;
        		case 'reward':
        			rowValue={
        				rewardName:'',
        				allowOrg:'',
        				allowTime:''
        			}
        			//添加新的行数
                    this.rewardList.push(rowValue);
        			break;
        		case 'leader':
        			rowValue={
        				userName:'',
        				positon:'',
        				allowLeaderTime:''
        			}
        			//添加新的行数
                    this.leaderList.push(rowValue);
        			break;
        	}
        		
        },
        deleteLine(tableName,scopeIndex){
        	
        	switch (tableName){
    		case 'dept':
    			this.deptInfoList.splice(scopeIndex, 1);
    			break;
    		case 'reward':
    			this.rewardList.splice(scopeIndex, 1);
    			break;
    		case 'leader':
    			this.leaderList.splice(scopeIndex, 1);
    			break;
        	}
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
                checkIds.push(entry.moduleId);
            }
            console.log(that.checkBoxOptions.roleModule,that.allModules,val,checkIds);
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
                checkIds.push(entry.roleId);
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
                    checkIds.push(entry.roleId);
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
                    checkIds.push(entry.roleId);
                }
            }
            let checkedCount = value.length;
            that.checkBoxAll.userRole = (checkedCount === checkIds.length);
            that.isIndeterminate.userRole = checkedCount > 0 && checkedCount < checkIds.length;
        },
        /**
         * 加载党委基本信息
         */
        loadDwjbxx(){
        	let that = this;
        	let treeTable =[];
        	that.loading.flag = true;
        	axios.get("/api/org/getOrgList",null).then(function(response){
        		if(parseInt(response.data.code) == 200 ){
        			let parentArr = response.data.result.filter(l => l.upperOrg === null);
        			that.dwjbxxTreeLevel.level = 0;
        			that.dwjbxxTableData = that.getTreeData(response.data.result, parentArr);
        			console.log(that.dwjbxxTableData);
        			that.loading.flag = false;
        		}else{
        			that.$message.error('数据加载失败');
        			that.loading.flag = false;
        		}
        	});
        },
        getUpperOrg(){
        	let that = this;
        	let treeTable =[];
        	axios.get("/api/org/getUpperOrgList",null).then(function(response){
        		if(parseInt(response.status) == 200 ){
        			for(let i =0 ; i <response.data.result.length;i++){
        				response.data.result[i].value=response.data.result[i].orgId;
        				response.data.result[i].label=response.data.result[i].orgName;
        			}
        			let parentArr = response.data.result.filter(l => l.upperOrg === null);
        			that.upperOrg = that.getTreeData(response.data.result, parentArr);
        			console.log(that.upperOrg);
        		}
        	});
        },
        getTreeDict(){
        	let that = this;
        	axios.get("/api/treeDict/getTreeDictByType",{params:{
        		treeType:'2'
            }}).then(function(response){
        		if(parseInt(response.status) == 200 ){
        			for(let i =0 ; i <response.data.result.length;i++){
        				response.data.result[i].value=response.data.result[i].nodeId;
        				response.data.result[i].label=response.data.result[i].nodeName;
        			}
        			let parentArr = response.data.result.filter(l => l.upperNode === null);
        			that.orgAddressRelation = that.getTreeDictData(response.data.result, parentArr);
        		}
        	});
        },
        getOptionDict(){
        	let that = this;
        	axios.get("/api/dict/search",{params:{
        		dictType:'leadTime'
            }}).then(function(response){
        		if(parseInt(response.status) == 200 ){
        			that.leadTime = response.data.data.list;
        		}
        	});
        	axios.get("/api/dict/search",{params:{
        		dictType:'orgType'
            }}).then(function(response){
        		if(parseInt(response.status) == 200 ){
        			that.orgType = response.data.data.list;
        		}
        	});
        	axios.get("/api/dict/search",{params:{
        		dictType:'isDelPartPersonAuth'
            }}).then(function(response){
        		if(parseInt(response.status) == 200 ){
        			that.isDelPartPersonAuth = response.data.data.list;
        		}
        	});
        	axios.get("/api/dict/search",{params:{
        		dictType:'elecType'
            }}).then(function(response){
        		if(parseInt(response.status) == 200 ){
        			that.elctType = response.data.data.list;
        		}
        	});
        },
        /**
         * 处理没有children结构的数据
         */
        getTreeDictData(list, dataArr) {
            dataArr.map((pNode, i) => {
              let childObj = []
              list.map((cNode, j) => {
                if (pNode.nodeId === cNode.upperNode) {
                  childObj.push(cNode)
                }
              })
              pNode.children = childObj
              if(pNode.children.length ==0){
            	  pNode.children = null;
              }
              if (childObj.length > 0) {
                this.getTreeDictData(list, childObj)
              }
            })
            return dataArr
          },
        /**
         * 处理没有children结构的数据
         */
        getTreeData(list, dataArr) {
            dataArr.map((pNode, i) => {
            	if(pNode.level == null){
            		pNode.level = 0 ;
            	}
              let childObj = []
              list.map((cNode, j) => {
                if (pNode.orgId === cNode.upperOrg) {
                	
                	cNode.level = pNode.level+1;
                  childObj.push(cNode)
                }
              })
              pNode.children = childObj
              if(pNode.children.length ==0){
            	  pNode.children = null;
              }
              if (childObj.length > 0) {
                this.getTreeData(list, childObj)
              }
            })
            return dataArr
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

        /**
         * 加载消息信息
         */
        loadNews(criteria, pageNum, pageSize) {
            let that = this;
            let _isViewed = null;
            if (that.formSearchNews.isViewed && that.formSearchNews.isViewed != '') {
                _isViewed = that.formSearchNews.isViewed;
            }
            axios.get("/api/news/getCurrUserNews",{params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize: pageSize,
                    isViewed: _isViewed,
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.newsArray = response.data.data.list;
                        that.pager.news.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleNewsSizeChange: function(val) {
            this.pager.news.pageSize = val;
            this.loadNews(this.pager.news.criteria, this.pager.news.currentPage, this.pager.news.pageSize);
        },

        //页码变更
        handleNewsCurrentChange: function(val) {
            this.pager.news.currentPage = val;
            this.loadNews(this.pager.news.criteria, this.pager.news.currentPage, this.pager.news.pageSize);
        },

        /**
         * 文本域只能输入数字(默认设置为0)
         * @param e
         */
        handleInputNumber: function(e) {
            //console.log('handleInputNumber=>', e.target, e.target.value);
            let a = e.target.value.replace(/[^\d]/g, "");
            if (!a) {
                e.target.value = 0;
            }

            // if (!a && e.keyCode != 8) {
            //     e.preventDefault();
            // }
        },

        /**
         * (消息)新增接收人
         */
        handleNewsAddReceivers: function(e) {
            let that = this;
            that.formNews.receiveUserIds = that.formNews.receiveUserIds || [];
            that.formNews.receiveUsers = that.formNews.receiveUsers || [];
            console.log('newsReceiveUserIds => ', that.newsReceiveUserIds);
            let _len = that.newsReceiveUserIds.length;
            if (_len > 0) {
                // 向表单中添加记录
                for (let i = 0; i < _len; i ++) {
                    let _newsReceiveUserId = that.newsReceiveUserIds[i];
                    if (that.newsReceiveUsers[_newsReceiveUserId]) {
                        that.formNews.receiveUserIds.push(_newsReceiveUserId);
                        that.formNews.receiveUsers.push(that.newsReceiveUsers[_newsReceiveUserId]);
                    }
                }
                console.log('that.formNews.receiveUserIds', that.formNews.receiveUserIds);
                console.log('that.formNews.receiveUsers', that.formNews.receiveUsers);
            }
            that.dialogShow.newsReceivers = !that.dialogShow.newsReceivers;
        },

        /**
         * (消息)移除接收人
         * @param _reUserId 用户ID
         */
        handleNewsRemoveReceiver: function(_reUserId) {
            _reUserId = _reUserId || '';
            let that = this;
            console.log('that.formNews', that.formNews);
            if (_reUserId && _reUserId != '') {
                let _len = that.formNews.receiveUsers.length;
                let _ids = [], _users = [];
                if (_len > 0) {
                    for (let i = 0; i < _len; i ++) {
                        let __user = that.formNews.receiveUsers[i];
                        // if (__user.userId === _reUserId) {
                        //     that.formNews.receiveUsers.splice(i, 1);
                        // }
                        if (__user.userId != _reUserId) {
                            _ids.push(__user.userId);
                            _users.push(__user);
                        }
                    }
                }
                that.formNews.receiveUsers = _users;
                that.formNews.receiveUserIds = _ids;
                that.$forceUpdate();
            }
        },

        /**
         * 按照阅读状态查询消息处理
         * @param _isViewed 是否已阅读
         */
        handleNewsSearchByViewed: function (_isViewed) {
            let that = this;
            _isViewed = _isViewed || '';
            _isViewed = (_isViewed != '1' && _isViewed != '0') ? '' : _isViewed;
            that.formSearchNews.isViewed = _isViewed;
            that.formSearchNews.viewedBtnType = (_isViewed == '1') ? 'info' : '';
            that.formSearchNews.unViewedBtnType = (_isViewed == '0') ? 'info' : '';
            that.formSearchNews.allBtnType = (_isViewed == '') ? 'info' : '';
            that.searchForm('formSearchNews');
        },

        /**
         * 查看消息
         * @param recordId 当前记录唯一标识
         */
        handleNewsView: function(_recordId) {
            let that = this;
            _recordId = _recordId || '';
            if ('' != _recordId) {
                let _record = {};
                for (let i = 0; i < that.newsArray.length; i ++) {
                    if (that.newsArray[i].recordId === _recordId) {
                        _record = that.newsArray[i];
                        break;
                    }
                }
                console.log('_record', _record);
                that.currNewsSendRecord = {
                    newId: _record.newsId || '',
                    title: _record.newsTitle || '',
                    content: _record.newsContent || '',
                };
                that.dialogShow.viewNews = !that.dialogShow.viewNews;
                if (_record.status && _record.status != 1) {
                    let params = new URLSearchParams();
                    params.append('recordId', _recordId);
                    axios.post("/api/news/viewNews", params)
                        .then(function(response){
                            if(parseInt(response.data.code) === 200){
                                that.searchForm('formSearchNews');
                                console.log('查看成功。。。', _recordId);
                            }
                        }).catch(function(err){
                        console.warn(err);
                    });
                }
            }

        },

        /**
         * 加载文章
         */
        loadArticles(criteria, pageNum, pageSize) {
            let that = this;
            axios.get("/api/article/search",{params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize:pageSize,
                    categoryId: that.formSearchArticle.categoryId,
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.articles = response.data.data.list;
                        that.pager.article.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleArticleSizeChange: function(val) {
            this.pager.article.pageSize = val;
            this.loadArticles(this.pager.article.criteria, this.pager.article.currentPage, this.pager.article.pageSize);
        },

        //页码变更
        handleArticleCurrentChange: function(val) {
            this.pager.article.currentPage = val;
            this.loadArticles(this.pager.article.criteria, this.pager.article.currentPage, this.pager.article.pageSize);
        },

        /**
         * 加载(当前用户)党费缴纳信息
         */
        loadPartyDues(criteria, pageNum, pageSize) {
            let that = this;
            axios.get("/api/dues/getCurrUserDues", {params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize:pageSize,
                }})
                .then(function(response){/*成功*/
                    if(parseInt(response.status) == 200 ) {
                        that.partyDuesArray = response.data.data.list;
                        that.pager.partyDues.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handlePartyDuesSizeChange: function(val) {
            this.pager.partyDues.pageSize = val;
            this.loadPartyDues(this.pager.partyDues.criteria, this.pager.partyDues.currentPage, this.pager.partyDues.pageSize);
        },

        //页码变更
        handlePartyDuesCurrentChange: function(val) {
            this.pager.partyDues.currentPage = val;
            this.loadPartyDues(this.pager.partyDues.criteria, this.pager.partyDues.currentPage, this.pager.partyDues.pageSize);
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
                that.formNews = config.formNews;
                that.formArticle = config.formArticle;
                that.formPartyDues = config.formPartyDues;

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
                that.formSearchNews = searchForm.news;
                that.formSearchPartyDues = searchForm.partyDues;
            })
            .catch(function(err){/*异常*/
                console.log(err);
            });
    },
    created: function () {
        //this.loadArticleTypes();
        this.getCurrentUserInfo();
    },
    beforeMount: function() {
        // this.getCurrentUserInfo();
    },
    mounted: function () {
        let that = this;

        that.getAllAuthRole();
        that.loadMemberUsers('','',0, that.pager.user.pageSize);
        that.ueditors.article = UE.getEditor('articleEditor', that.ueditorConfig);
        that.ueditors.article.addListener("ready", function () {
            that.ueditors.article.setContent('你好nihao ', false); // 确保UE加载完成后，放入内容。
        });
    },
    destroyed: function() {
        this.ueditors.article.destroy();
    }
})
