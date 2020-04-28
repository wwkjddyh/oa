const CurrentChartId = 'bigDataChartId';
let ws = null;
let heartCheck = {
    timeout: 60000,//60ms
    timeoutObj: null,
    serverTimeoutObj: null,
    reset: function(){
        clearTimeout(this.timeoutObj);
        clearTimeout(this.serverTimeoutObj);
        this.start();
    },
    start: function(){
        let self = this;
        self.timeoutObj = setTimeout(function(){
            ws.send("HeartBeat");
            self.serverTimeoutObj = setTimeout(function(){
                ws.close();//如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
            }, self.timeout)
        }, self.timeout)
    },
}
new Vue({
    el: '#admin-main',
    watch: {
        // filterText(val) {
        //     this.$refs.tree2.filter(val);
        // },

        /**
         * 监控接收者名称变化
         * @param val
         */
        receiverUserName(val) {
            this.$refs.receiverTree.filter(val);
        },

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

            */

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
                    that.getNddyxxOptions();
                    if(that.isSuperAdmin){
                  		that.getAdminUpperOrg();
                  	}else{
                  		that.getUserUpperOrgList();
                  	}
                    that.loadSysUsers('',1, that.pager.sysUser.pageSize);
                    break;
                case 'dwjbxx':
                	that.getTreeDict();
                	that.getRewardTreeDict();
                	that.getOptionDict();
                	that.dwjbxxMain = false;
                	that.loadDwjbxx();
                	break;
                case 'announce':
                    that.getAllMsgCategories();
                    that.loadNews('', 1, that.pager.news.pageSize);
                    break;
                case 'articles':    // 简报列表
                    //that.getArticleCategories();
                    that.currentArticleFormTitle = '简报';
                    
                    that.formSearchArticle.isBrief = true;
                    that.formSearchArticle.sendType = '1';
                    that.formSearchArticle.categoryId = '53c34dec-7447-4bbc-9ff3-af0f0686b07f';
                    that.newsOperate=false;
            		that.operateName = '操作';
            		that.operateIcon = 'el-icon-setting';
                    //that.loadArticles('',1, that.pager.article.pageSize);
                    that.currAction = 'append';
                    that.def_menu_id = 'articles';
                    that.receiveArtcleType='info';
                    that.sendArtcleType='';
                    that.loadCurrUserReceiverBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,null);
                    break;
                case 'xxjl':
                	//that.getArticleCategories();
                    that.currentArticleFormTitle = '学习交流';
                    that.formSearchArticle.isBrief = true;
                    that.formSearchArticle.sendType = '2';
                    that.formSearchArticle.categoryId = '63c34dec-7447-4bbc-9ff3-af0f0686b07f';
                    that.newsOperate=false;
            		that.operateName = '操作';
            		that.operateIcon = 'el-icon-setting';
                    //that.loadArticles('',1, that.pager.article.pageSize);
                    that.currAction = 'append';
                    that.def_menu_id = 'xxjl';
                    that.receiveArtcleType='info';
                    that.toApprove='',
            		that.approveSuccess='',
            		that.approveFail='',
                    that.sendArtcleType='';
                    that.loadCurrUserReceiverBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
                    break;
                case 'articleManagement':   // 文章管理
                    that.getArticleCategories();
                    that.currentArticleFormTitle = '文章';
                    that.formSearchArticle.isBrief = false;
                    that.formSearchArticle.sendType = '1';
                    that.formSearchArticle.categoryId = '53c34dec-7447-4bbc-9ff3-af0f0686b07f';
                    that.loadArticles('',1, that.pager.article.pageSize);
                    that.currAction = 'append';
                    that.def_menu_id = 'articleManagement';
                    break;
                case 'formArticle': // 简报或文章
                	//that.getArticleCategories();
                    that.formArticle.categoryId = that.formSearchArticle.categoryId
                    that.formSearchArticle.categoryId = that.formSearchArticle.categoryId;
                    if(that.currAction == 'edit') {
                        setTimeout(function() {
                            that.ueditors.article.setContent(that.formArticle.content, false);
                        }, 500);
                        that.formArticle.sendType = '1';    // 设置默认为简报
                        that.currAction = 'append';
                        //that.def_menu_id = 'formArticle';
                    }
                    else {
                        setTimeout(function() {
                            if (that.def_menu_id === 'articles') { /*简报*/
                                that.ueditors.article.setContent(that.defaultBriefContent, false);
                            }
                            else {
                                that.ueditors.article.setContent('', false);
                            }
                        }, 500);
                        that.currAction = 'append';
                        that.formArticle = {
                            isEdit : false,
                            recordId: '',
                            categoryId: that.formSearchArticle.categoryId, //简报
                            categoryName: '',
                            title: '',
                            intro: '',
                            content: '',
                            tags: '',
                            source: '',
                            authorName: '',
                            sourceSite: '',
                            creatorId: '',
                            creatorName: '',
                            updatorId: '',
                            updatorName: '',
                            updateTime: '',
                            recordTime: '',
                            sendType: '1',
                            receiverIds: [],
                            receiveUsers: [],
                            receiverIdArrStr: '',
                        };

                        that.briefReceiveUserIds = [];
                        console.log('that.formArticle203', that.formArticle);
                    }
                    break;
                case 'nddyxxcj':
                	that.setDyxxYear();
                	that.dwjbxxMain = false;
                	if(that.isSuperAdmin){
                  		that.getAdminUpperOrg();
                  	}else{
                  		that.getUserUpperOrgList();
                  	}
                	//that.getUpperOrg();
                	//that.loadDwjbxx();
                	that.getNddyxxOptions();
                	//that.loadNddyxxcj();
                	break;

                /*
                case 'ndsjdfqk':
                    that.loadPartyDues('', 1, that.pager.partyDues.pageSize);
                    break;*/
                case 'dfglzlxz':    //党费资料下载
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '01ef5219-464e-44a3-890a-557e3bbabd4e';
                    that.formSearchRes.typeId = '01ef5219-464e-44a3-890a-557e3bbabd4e';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '党费管理资料下载';
                    that.uploadData = {
                        name: '',
                        type: 'res-dfgl',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    break;
                case 'zlk':    // 资料库
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '2e9941a0-2a6f-4c2f-b74c-970d0351469f';
                    that.formSearchRes.typeId = '2e9941a0-2a6f-4c2f-b74c-970d0351469f';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '资料库';
                    that.uploadData = {
                        name: '',
                        type: 'res-dygl',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    break;
                case 'hjglzlxz':    // 换届管理资料下载
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '3dea99ab-ec00-4633-b24c-7c44a5ce57b8';
                    that.formSearchRes.typeId = '3dea99ab-ec00-4633-b24c-7c44a5ce57b8';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '换届管理资料下载';
                    that.uploadData = {
                        name: '',
                        type: 'res-hjgl',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    break;
                case 'dyglzlxz':    // 党员管理资料下载
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '1e9941a0-2a6f-4c2f-b74c-970d0351469f';
                    that.formSearchRes.typeId = '1e9941a0-2a6f-4c2f-b74c-970d0351469f';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '党员管理资料下载';
                    that.uploadData = {
                        name: '',
                        type: 'res-dygl',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    break;
                case 'dyfgzd':    // 党员管理相关法规制度
                    that.formSearchRes.key = '';
                    that.formRes.typeId = 'fa0921b0-1646-4af1-b590-d0a5d8bdce43';
                    that.formSearchRes.typeId = 'fa0921b0-1646-4af1-b590-d0a5d8bdce43';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '党员管理相关法规制度';
                    that.uploadData = {
                        name: '',
                        type: 'res-dyfgzd',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    break;
                case 'dzzfgzd':    // 党组织管理相关法规制度
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '9234abe3-8999-45b8-8ad5-55409946e547';
                    that.formSearchRes.typeId = '9234abe3-8999-45b8-8ad5-55409946e547';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '党组织管理相关法规制度';
                    that.uploadData = {
                        name: '',
                        type: 'res-dzzfgzd',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    break;
                case 'dffgzd':    // 党费管理相关法规制度
                    that.formSearchRes.key = '';
                    that.formRes.typeId = 'e78acbc2-ea9d-4589-b833-0472e532a61a';
                    that.formSearchRes.typeId = 'e78acbc2-ea9d-4589-b833-0472e532a61a';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '党费管理相关法规制度';
                    that.uploadData = {
                        name: '',
                        type: 'res-dffgzd',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    break;
                case 'hjfgzd':    // 换届管理相关法规制度
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '598f7307-104d-4cc1-b0e9-d61749286c5e';
                    that.formSearchRes.typeId = '598f7307-104d-4cc1-b0e9-d61749286c5e';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '换届管理相关法规制度';
                    that.uploadData = {
                        name: '',
                        type: 'res-hjfgzd',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    break;
                case 'nddfszqkgs':    // 年度党费收支情况公示
                    that.getResOtherTypes();
                    that.resTableView = false;
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '4bfeb907-05c0-48a5-9719-70d07eb640a2';
                    that.formRes.typeName = '年度党费收支情况公示';
                    that.formSearchRes.typeId = '4bfeb907-05c0-48a5-9719-70d07eb640a2';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '年度党费收支情况公示';
//                    that.formSearchRes.yearMonth = that.getCurrYearMonth();
                    that.uploadData = {
                        name: '',
                        type: 'res2-nddfszqkgs',
                        parse: '1',
                    };
//                    that.loadResList('', 1, that.pager.res.pageSize);
                    that.getCommitteeData();
                    break;
                case 'nddfszjcqk':    // 年度党费收支结存情况
                    that.getResOtherTypes();
                    that.resTableView = false;
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '0737d01e-b6f9-4567-8892-63fa9071903f';
                    that.formRes.typeName = '年度党费收支结存情况';
                    that.formSearchRes.typeId = '0737d01e-b6f9-4567-8892-63fa9071903f';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '年度党费收支结存情况';
//                    that.formSearchRes.yearMonth = that.getCurrYearMonth();
                    that.uploadData = {
                        name: '',
                        type: 'res2-nddfszjcqk',
                        parse: '1',
                    };
                    that.getCommitteeData();
                    break;
                case 'ndhjgzjh':    // 年度换届工作计划
                    that.getResOtherTypes();
                    that.resTableView = false;
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '3d4565b4-041d-44e0-b411-b441865047c7';
                    that.formRes.typeName = '年度换届工作计划';
                    that.formSearchRes.typeId = '3d4565b4-041d-44e0-b411-b441865047c7';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '年度换届工作计划';
//                    that.formSearchRes.yearMonth = that.getCurrYearMonth();
                    that.uploadData = {
                        name: '',
                        type: 'res2-ndhjgzjh',
                        parse: '1',
                    };
//                    that.loadResList('', 1, that.pager.res.pageSize);
                    that.getCommitteeData();
                    break;
                case 'hjgztz':    // 换届工作台账
                    that.getResOtherTypes();
                    that.resTableView = false;
                    that.formSearchRes.key = '';
                    that.formRes.typeId = '4a056958-6d34-4dbc-ac12-1385b0745023';
                    that.formRes.typeName = '换届工作台账';
                    that.formSearchRes.typeId = '4a056958-6d34-4dbc-ac12-1385b0745023';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '换届工作台账';
//                    that.formSearchRes.yearMonth = that.getCurrYearMonth();
                    that.uploadData = {
                        name: '',
                        type: 'res2-hjgztz',
                        parse: '1',
                    };
//                    that.loadResList('', 1, that.pager.res.pageSize);
                    that.getCommitteeData();
                    break;
                case 'ndsjdfqk':    // 年度党费上缴情况
                    that.getResOtherTypes();
                    that.resTableView = false;
                    that.formSearchRes.key = '';
                    that.formRes.typeId = 'e5e6bc19-d214-49fd-9cfe-0317b8e1a6c5';
                    that.formRes.typeName = '年度党费上缴情况';
                    that.formSearchRes.typeId = 'e5e6bc19-d214-49fd-9cfe-0317b8e1a6c5';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '年度党费上缴情况';
//                    that.formSearchRes.yearMonth = that.getCurrYearMonth();
                    that.uploadData = {
                        name: '',
                        type: 'res2-ndsjdfqk',
                        parse: '1',
                    };
//                    that.loadResList('', 1, that.pager.res.pageSize);
                    that.getCommitteeData();
                    break;
                case 'fzdy':    // 发展党员
                    that.getResOtherTypes();
                    that.fzdyLoading = false;
                    that.loadDwjbxx();
                    that.formSearchRes.key = '';
                    that.formRes.typeId = 'ed535138-6ec2-468c-8083-d967a24c2f33';
                    that.formRes.typeName = '发展党员';
                    that.formSearchRes.typeId = 'ed535138-6ec2-468c-8083-d967a24c2f33';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '发展党员';
                    that.formSearchRes.yearMonth = that.getCurrYearMonth();
                    that.uploadData = {
                        name: '',
                        type: 'res2-fzdy',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                    that.resetPrePartyMembersStyle();
                    break;
                case 'bigData':     // 大数据
                    //that.getCurrUserEchartsData();

                    // 初始化组织列表<修改前显示方案(2020/04/02)>
                    if (that.currUserEchartsData['sex']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['sex']));
                        let _tSubTitleArr = _tChartData.legend || [];
                        let _legendLen = _tSubTitleArr.length;
                        if (_legendLen == 1) {
                            that.currUserLegendArr = _tSubTitleArr;
                            that.currUserLegendChange = _tSubTitleArr[0];
                        }
                        else {
                            that.currUserLegendArr = ['全部'].concat( _tSubTitleArr);
                        }
                    }

                    /* //修改前显示方案(2020/04/02)
                    let sexChart = {
                        id: 'bigDataChartId',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        seriesData: [],
                    };
                    if (that.currUserEchartsData['sex']) {
                        let _tChartData = that.currUserEchartsData['sex'];
                        console.log('_tChartData', _tChartData);
                        sexChart.title = _tChartData.title || '';
                        let _subtitle = '';
                        let _tSubTitleArr = _tChartData.legend || [];
                        let _legendLen = _tSubTitleArr.length;
                        if (_legendLen == 1) {
                            that.currUserLegendArr = _tSubTitleArr;
                        }
                        else {
                            that.currUserLegendArr = ['全部'].concat( _tSubTitleArr);
                        }
                        if (_legendLen == 1) {
                            if (_tSubTitleArr.length > 0) {
                                _subtitle = _tSubTitleArr[0];
                            }
                            sexChart.subtitle = _subtitle;
                            let _legendArr = _tChartData.axis || [];
                            sexChart.legendData = _legendArr;
                            let _seriesData = [];
                            if (_tChartData.data) {
                                let _seriesArr = _tChartData.data || [];
                                if (_seriesArr.length > 0) {
                                    let _sData = _seriesArr[0].data || [];
                                    _seriesData = [
                                        {value: parseInt(_sData[0]), name: _legendArr[0]},
                                        {value: parseInt(_sData[1]), name: _legendArr[1]}
                                    ];
                                }
                            }
                            sexChart.seriesData = _seriesData;

                            that.initEChartsPieChart(sexChart.id, sexChart.title,
                                sexChart.subtitle, sexChart.legendData, sexChart.seriesData);
                        }
                        else if (_legendLen > 1) {
                            sexChart.legendData = _tChartData.legend || [];
                            sexChart.axis = _tChartData.axis || [];
                            let _seriesData = [];
                            if (_tChartData.data) {
                                let _seriesArr = _tChartData.data || [];
                                let _seriesLen = _seriesArr.length;
                                if (_seriesLen > 0) {
                                    for (let i = 0; i < _seriesLen; i ++) {
                                        let _sData = _seriesArr[i];
                                        _seriesData.push({
                                            name: _sData.name || '',
                                            data: _sData.data || [],
                                        })
                                    }
                                }
                            }
                            sexChart.seriesData = _seriesData;
                            that.initEChartsCloumnChart5(sexChart);
                        }

                    }
                    let ageChart = {
                        id: 'bigDataChartId2',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: [],
                    };
                    if (that.currUserEchartsData['age']) {
                        let _tChartData = that.currUserEchartsData['age'];
                        ageChart.title = _tChartData.title || '';
                        let _tSubTitleArr = _tChartData.legend || [];
                        let _legendLen = _tSubTitleArr.length;
                        if (_legendLen == 1) {
                            that.currUserLegendArr = _tSubTitleArr;
                        }
                        else {
                            that.currUserLegendArr = ['全部'].concat( _tSubTitleArr);
                        }
                        if (_legendLen == 1) {
                            let _subtitle = '';
                            if (_tSubTitleArr.length > 0) {
                                _subtitle = _tSubTitleArr[0];
                            }
                            ageChart.subtitle = _subtitle;
                            ageChart.xAxisData = _tChartData.axis || [];

                            let _seriesData = [];
                            let _sDataArr = _tChartData.data || [];
                            if (_sDataArr.length > 0) {
                                for (let i = 0; i < _sDataArr.length; i++) {
                                    let _sData = _sDataArr[i];
                                    _seriesData.push(_sData['data']);
                                }
                            }
                            ageChart.seriesData = _seriesData.length > 0 ? _seriesData[0] : [];
                            that.initEChartsCloumnChart2(ageChart.id, ageChart.title, ageChart.subtitle,
                                ageChart.legendData, ageChart.xAxisData, ageChart.seriesData);
                        }
                        else if (_legendLen > 1) {
                            ageChart.legendData = _tChartData.legend || [];
                            ageChart.axis = _tChartData.axis || [];
                            let _seriesData = [];
                            if (_tChartData.data) {
                                let _seriesArr = _tChartData.data || [];
                                let _seriesLen = _seriesArr.length;
                                if (_seriesLen > 0) {
                                    for (let i = 0; i < _seriesLen; i ++) {
                                        let _sData = _seriesArr[i];
                                        _seriesData.push({
                                            name: _sData.name || '',
                                            data: _sData.data || [],
                                        })
                                    }
                                }
                            }
                            ageChart.seriesData = _seriesData;
                            that.initEChartsCloumnChart5(ageChart);
                        }

                    }

                    let partyAgeChart = {
                        id: 'bigDataChartId3',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: []
                    };
                    if (that.currUserEchartsData['partyAge']) {
                        let _tChartData = that.currUserEchartsData['partyAge'];
                        partyAgeChart.title = _tChartData.title || '';
                        let _subtitle = '';
                        let _tSubTitleArr = _tChartData.legend || [];
                        let _legendLen = _tSubTitleArr.length;
                        if (_legendLen == 1) {
                            that.currUserLegendArr = _tSubTitleArr;
                        }
                        else {
                            that.currUserLegendArr = ['全部'].concat( _tSubTitleArr);
                        }
                        if (_legendLen == 1) {
                            if (_tSubTitleArr.length > 0) {
                                _subtitle = _tSubTitleArr[0];
                            }
                            partyAgeChart.subtitle = _subtitle;
                            partyAgeChart.xAxisData = _tChartData.axis || [];

                            let _seriesData = [];
                            let _sDataArr = _tChartData.data || [];
                            if (_sDataArr.length > 0) {
                                for (let i = 0; i < _sDataArr.length; i++) {
                                    let _sData = _sDataArr[i];
                                    _seriesData.push(_sData['data']);
                                }
                            }
                            partyAgeChart.seriesData = _seriesData.length > 0 ? _seriesData[0] : [];

                            that.initEChartsCloumnChart2(partyAgeChart.id, partyAgeChart.title, partyAgeChart.subtitle,
                                partyAgeChart.legendData, partyAgeChart.xAxisData, partyAgeChart.seriesData);
                        }
                        else if (_legendLen > 1) {
                            partyAgeChart.legendData = _tChartData.legend || [];
                            partyAgeChart.axis = _tChartData.axis || [];
                            let _seriesData = [];
                            if (_tChartData.data) {
                                let _seriesArr = _tChartData.data || [];
                                let _seriesLen = _seriesArr.length;
                                if (_seriesLen > 0) {
                                    for (let i = 0; i < _seriesLen; i ++) {
                                        let _sData = _seriesArr[i];
                                        _seriesData.push({
                                            name: _sData.name || '',
                                            data: _sData.data || [],
                                        })
                                    }
                                }
                            }
                            partyAgeChart.seriesData = _seriesData;
                            that.initEChartsCloumnChart5(partyAgeChart);
                        }
                    }

                    let educationChart = {
                        id: 'bigDataChartId4',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: [],
                    };

                    if (that.currUserEchartsData['education']) {
                        let _tChartData = that.currUserEchartsData['education'];
                        educationChart.title = _tChartData.title || '';
                        let _subtitle = '';
                        let _tSubTitleArr = _tChartData.legend || [];
                        let _legendLen = _tSubTitleArr.length;
                        if (_legendLen == 1) {
                            that.currUserLegendArr = _tSubTitleArr;
                        }
                        else {
                            that.currUserLegendArr = ['全部'].concat( _tSubTitleArr);
                        }
                        if (_legendLen == 1) {
                            if (_tSubTitleArr.length > 0) {
                                _subtitle = _tSubTitleArr[0];
                            }
                            educationChart.subtitle = _subtitle;
                            educationChart.xAxisData = _tChartData.axis || [];

                            let _seriesData = [];
                            let _sDataArr = _tChartData.data || [];
                            if (_sDataArr.length > 0) {
                                for (let i = 0; i < _sDataArr.length; i++) {
                                    let _sData = _sDataArr[i];
                                    _seriesData.push(_sData['data']);
                                }
                            }
                            educationChart.seriesData = _seriesData.length > 0 ? _seriesData[0] : [];

                            that.initEChartsCloumnChart2(educationChart.id, educationChart.title, educationChart.subtitle,
                                educationChart.legendData, educationChart.xAxisData, educationChart.seriesData);
                        }
                        else if (_legendLen > 1) {
                            educationChart.legendData = _tChartData.legend || [];
                            educationChart.axis = _tChartData.axis || [];
                            let _seriesData = [];
                            if (_tChartData.data) {
                                let _seriesArr = _tChartData.data || [];
                                let _seriesLen = _seriesArr.length;
                                if (_seriesLen > 0) {
                                    for (let i = 0; i < _seriesLen; i ++) {
                                        let _sData = _seriesArr[i];
                                        _seriesData.push({
                                            name: _sData.name || '',
                                            data: _sData.data || [],
                                        })
                                    }
                                }
                            }
                            educationChart.seriesData = _seriesData;
                            that.initEChartsCloumnChart5(educationChart);
                        }

                    }*/

                    //修改后显示方案(2020/04/02)
                    that.handleBigDataChange(that.currUserLegendChange);

                    // console.log('that.chartsData', sexChart, ageChart, partyAgeChart, educationChart)
                    // that.initEChartsPieChart(sexChart.id, sexChart.title,
                    //     sexChart.subtitle, sexChart.legendData, sexChart.seriesData);
                    // // that.initEChartsCloumnChart(ageChart.id, ageChart.title, ageChart.subtitle,
                    // //     ageChart.legendData, ageChart.xAxisData, ageChart.seriesData);
                    // // that.initEChartsCloumnChart(partyAgeChart.id, partyAgeChart.title, partyAgeChart.subtitle,
                    // //     partyAgeChart.legendData, partyAgeChart.xAxisData, partyAgeChart.seriesData);
                    // // that.initEChartsCloumnChart(educationChart.id, educationChart.title, educationChart.subtitle,
                    // //     educationChart.legendData, educationChart.xAxisData, educationChart.seriesData);
                    //
                    // that.initEChartsCloumnChart2(ageChart.id, ageChart.title, ageChart.subtitle,
                    //     ageChart.legendData, ageChart.xAxisData, ageChart.seriesData);
                    // that.initEChartsCloumnChart2(partyAgeChart.id, partyAgeChart.title, partyAgeChart.subtitle,
                    //     partyAgeChart.legendData, partyAgeChart.xAxisData, partyAgeChart.seriesData);
                    // that.initEChartsCloumnChart2(educationChart.id, educationChart.title, educationChart.subtitle,
                    //     educationChart.legendData, educationChart.xAxisData, educationChart.seriesData);

                    /*
                    that.initEChartsCloumnChart3('bigDataChartId5', '', '',
                        [], [], []);
                    that.initEChartsCloumnChart2('bigDataChartId5', educationChart.title, educationChart.subtitle,
                        educationChart.legendData, educationChart.xAxisData, educationChart.seriesData);


                    that.initEChartsCloumnChart2('bigDataChartId5', educationChart.title, educationChart.subtitle,
                        educationChart.legendData, educationChart.xAxisData, educationChart.seriesData);
                    that.initEChartsCloumnChart4();
                     */

                    /*
                    if (that.chartsData) {
                        for (let i = 0; i < that.chartsData.length; i ++) {
                            let _chartData = that.chartsData[i];
                            let _chartId = _chartData.id;
                            if (i == 0) {
                                that.initEChartsPieChart(_chartId, _chartData.title,
                                    _chartData.subtitle, _chartData.legendData, _chartData.seriesData);
                            }
                            else {
                                that.initEChartsCloumnChart(_chartId, _chartData.title, _chartData.subtitle,
                                    _chartData.legendData, _chartData.xAxisData, _chartData.seriesData);
                            }
                        }
                    }

                     */
                    break;
                case 'faceMeet':    // 视频会议
                    break;
                case 'firstPage':
                	//that.firstPageDyzlxz();
                	that.formSearchArticle.sendType = '1';
                    that.formSearchArticle.categoryId = '53c34dec-7447-4bbc-9ff3-af0f0686b07f';
                    //that.loadArticles('',1, that.pager.article.pageSize);
                    /*that.currAction = 'append';
                    that.def_menu_id = 'articles';*/
                    that.loadCurrUserReceiverBriefRecord(that.formSearchBriefSendRecord.key, 1, 5,null);
                    that.loadCurrUserReceiverBriefRecord(that.formSearchBriefSendRecord.key, 1, 3,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
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
        headerTitle: '基层党建工作信息化系统',
        menuCollapseDivClass: 'menu-collapse-div',
        menuCollapseIcon: 'el-icon-s-fold',
        activeTabName: 'first',
        cronTabActiveName: 'second',    /*cron表达式tabs设置*/
        showContent: 'firstPage',
        receiverUserName: '',
        scrollBoxContent: '',
        defaultBriefContent: '<p style="text-align: center; margin-bottom: 20px;">\n' +
            '    <span style="font-family: 宋体, SimSun;"><strong><span style="font-size: 48px; color: red;">党 建 动 态</span></strong></span>\n' +
            '</p >\n' +
            '<p style="text-align:center">\n' +
            '    <span style="font-size: 19px; font-family: 宋体; color: red;">&nbsp; &nbsp;XXXX年XX期&nbsp; &nbsp; &nbsp; &nbsp; 中共天津市住房和城乡建设委员会委员会</span>\n' +
            '</p >\n' +
            '<p style="text-align: center;">\n' +
            '    <span style="font-size: 19px; font-family: 宋体; text-decoration: underline; color: rgb(255, 0, 0);">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span style="color: rgb(255, 0, 0); font-family: 宋体; font-size: 19px; text-align: center; text-decoration-line: underline;">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>\n' +
            '</p >',
        // defaultBriefContent: '',
        currentArticleFormTitle: '文章',
        currentChartId : CurrentChartId,
        currUserEchartsData: {   /*当前用户图片数据*/
            "sex": {},
            "age": {},
            "partyAge": {},
            "education": {}
        },
        currUserLegendArr: ['全部'],
        currUserLegendChange: '全部',
        partyMemberStat: {
            '男': 50,
            '女': 30,
        },
        charts: [
            {
                id: 'bigDataChartId',
                chart: null
            },
            {
                id: 'bigDataChartId2',
                chart: null
            },
            {
                id: 'bigDataChartId3',
                chart: null
            },
            {
                id: 'bigDataChartId4',
                chart: null
            },
        ],
        chartsData: [
            {
                id: 'bigDataChartId',
                title: '党员性别统计',
                subtitle: 'XXXX党组织',
                legendData: ['男士', '女士'],
                seriesData: [
                    {value:30, name:'男'},
                    {value:25, name:'女'}
                ],
            },
            {
                id: 'bigDataChartId2',
                title: '年龄',
                subtitle: 'XXX党组织',
                legendData: ['20~30', '30~40', '40~50', '50~60', '60以上'],
                xAxisData: ['2016年', '2017年', '2018年', '2019年', '2020年'],
                seriesData: [
                    [30, 33, 61, 34, 30],
                    [30, 32, 31, 34, 30],
                    [20, 12, 11, 24, 20],
                    [10, 22, 21, 14, 10],
                    [18, 17, 11, 9, 40],
                ],
            },
            {
                id: 'bigDataChartId3',
                title: '党龄',
                subtitle: 'XXX党组织',
                legendData: ['0~5年', '5～10年', '10～15年', '15～20年', '20年以上'],
                xAxisData: ['2016年', '2017年', '2018年', '2019年', '2020年'],
                seriesData: [
                    [20, 35, 12, 34, 30],
                    [20, 32, 11, 24, 20],
                    [15, 32, 21, 14, 10],
                    [24, 36, 11, 29, 20],
                    [28, 36, 11, 29, 29]
                ]
            },
            {
                id: 'bigDataChartId4',
                title: '学历',
                subtitle: 'XXX党组织',
                legendData: ['大专以下', '大专', '本科', '研究生', '博士', '其他'],
                xAxisData: ['2016年', '2017年', '2018年', '2019年', '2020年'],
                seriesData: [
                    [30, 32, 31, 34, 30],
                    [20, 12, 11, 24, 20],
                    [10, 22, 21, 24, 10],
                    [18, 17, 11, 19, 20],
                    [18, 17, 11, 19, 20],
                    [18, 17, 11, 19, 20]
                ],
            }
        ],
        // 即时聊天配置
        chatConfig: {},
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
        formnddyxxcjimport: [],
        upperOrg:[],
        currentUser: {},        // 当前用户信息
        currentUserStr: "",
        userOwnedModuleMaps: {},  //  当前用户所拥有(的所有角色)的模块<Map格式>
        userOwnedModules: [],    //  当前用户所拥有(的所有角色)的模块
        userOwnedMenus: [],     // 当前用户所拥有(的所有角色)的菜单
        formUser: {},
        formSysUser: {},
        formUserType: {},
        formUserDetail: {},
        formMember: {},
        formOperateLog: {},
        formnddyxxcj:{
        	isLeader:'0'
        },
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
        formRes: {},
        formResDl: {},
        formModifyPwd: {},
        formBriefSendRecord: {},
        formPrePartyMemeber: {},
        loading:{},
        fzdyListShow: false,
        fzdyPrePartyMemberAdd: false,
        fzdyPrePartyMemberSetps: false,
        nddyxxcjLoading:false,
        dwjbxxLoading: false,
        fullscreenLoading:false,
        fzdyLoading:false,
        fzdyCurrentOrg:'',
        sysUserLoading:false,
        newsLoading:false,
        currentUserOrgId:'',
        partyImg:{
        	width:'190px',
        	height:'250px',
        	position:'absolute',
        	background:'url(images/login.jpg)',
        	right:'4.5%',
        	top:'8%'
        },
        dyxxyear:{
        	year:''
        },
        firstPage1:[
            {
                index:0,
                name:'党组织管理',
                url:'dwjbxx',
                imgUrl:'/images/icon/orgadmin.png',
                modelName:'党委基本信息'
            },
            {
                index:1,
                name:'党员管理',
                url:'nddyxxcj',
                imgUrl:'/images/icon/dangyuanguanli.png',
                modelName:'年度党员信息采集'
            },
            {
                index:2,
                name:'党费管理',
                url:'nddfszqkgs',
                imgUrl:'/images/icon/dues.png',
                modelName:'年度党费收支情况公示'
            },
            {
                index:3,
                name:'换届管理',
                url:'hjgztz',
                imgUrl:'/images/icon/huanjie.png',
                modelName:'换届工作台账'
            }
            /*
            {
                index:0,
                name:'通知公告',
                url:'announce',
                modelName:'通知公告',
                imgUrl:'/images/icon/news.png'
            },
            {
                index:1,
                name:'简报',
                url:'articles',
                imgUrl:'/images/icon/brief.png',
                modelName:'简报'
            },
            {
                index:2,
                name:'视频会议',
                url:'faceMeet',
                imgUrl:'/images/icon/facemeet.png',
                modelName:'视频会议'
            },
            {
                index:3,
                name:'大数据',
                url:'bigData',
                imgUrl:'/images/icon/bigdata.png',
                modelName:'大数据'
            }*/
        ],
        firstPage3:[
            {
                index:0,
                name:'视频会议',
                url:'faceMeet',
                imgUrl:'/images/icon/facemeet.png',
                modelName:'视频会议'
            },
            {
                index:1,
                name:'即时通讯',
                url:'chat',
                imgUrl:'/images/icon/chat.png',
                modelName:'即时通讯'
            },
            {
                index:2,
                name:'统计分析',
                url:'bigData',
                imgUrl:'/images/icon/bigdata.png',
                modelName:'统计分析'
            },
            {
                index:3,
                name:'用户管理',
                url:'sysUsers',
                imgUrl:'/images/icon/user.png',
                modelName:'系统用户'
            }
            
            /*
            {
                index:0,
                name:'通知公告',
                url:'announce',
                modelName:'通知公告',
                imgUrl:'/images/icon/news.png'
            },
            {
                index:1,
                name:'简报',
                url:'articles',
                imgUrl:'/images/icon/brief.png',
                modelName:'简报'
            },
            {
                index:2,
                name:'视频会议',
                url:'faceMeet',
                imgUrl:'/images/icon/facemeet.png',
                modelName:'视频会议'
            },
            {
                index:3,
                name:'大数据',
                url:'bigData',
                imgUrl:'/images/icon/bigdata.png',
                modelName:'大数据'
            }*/
        ],
        firstPage2:[
        	{
                index:0,
                name:'通知公告',
                url:'announce',
                imgUrl:'/images/icon/news.png',
                modelName:'通知公告'
            },
            {
                index:1,
                name:'党建动态',
                url:'articles',
                imgUrl:'/images/icon/brief.png',
                modelName:'党建动态'
            },
            {
                index:2,
                name:'学习交流',
                url:'xxjl',
                imgUrl:'/images/icon/zswj.png',
                modelName:'学习交流'
            },
            {
                index:3,
                name:'资料库',
                url:'zlk',
                imgUrl:'/images/icon/zlk.png',
                modelName:'资料库'
            }
            
        ],
        firstPageFuncModules: [ /*首页功能模块区域*/
            /*
            {
                index: 1,
                name:'通知公告',
                url: 'announce',
                imgUrl:'/images/icon/news.png',
                modelName:'通知公告',
                iconClass: 'iconfont icon-MESSAGEREAD',
            },
             */
            {
                index:2,
                name:'视频会议',
                url:'faceMeet',
                imgUrl:'/images/icon/facemeet.png',
                modelName:'视频会议',
                iconClass: 'iconfont icon-shipinhuiyi',
            },
            {
                index:3,
                name:'大数据',
                url:'bigData',
                imgUrl:'/images/icon/bigdata.png',
                modelName:'大数据',
                iconClass: 'iconfont icon-dashuju',
            },
            {
                index: 4,
                name:'用户管理',
                url: 'sysUsers',
                imgUrl:'',
                modelName:'用户管理',
                iconClass: 'iconfont icon-yonghuguanli',
            },
        ],
        yearList:[],
        yearMonths: [],
        nddyxxcjYear:false,
        nddyxxSearchCondition:'',
        formnddyxxcjyear:{},
        sendArtcleType:'',
        receiveArtcleType:'info',
        formPartInfo:{},
        gender:[
        	{
        		value: '1',
        		label: '男'
        	},
        	{
        		value: '0',
        		label: '女'
        	}
        ],
        isLeader:[
        	{
        		value: '1',
        		label: '是'
        	},
        	{
        		value: '0',
        		label: '否'
        	}
        ],
        imageInfo:[],
        nddyxxcjLoading: false,
        dialogImageUrl: '',
        dialogVisible: false,
        singleImg:true,
        nddyxxcTableText:'',
        currUserChat: { /*当前用户聊天信息相关*/
            chatShow: true,
            friends: [
                {
                    userId : '001',
                    userName: '路人甲',
                    avatar: '🌋',
                    avatarUrl: '',
                    date: '2020/04/14',
                    msg: '你在么？',
                    isCurrent: true,
                    type: '1',  /*聊天类型：用户对用户*/
                    msgArr: [   /*最新消息列表*/
                        {
                            recordId: '00001',
                            t: "1",
                            msg: '你今天开心么？',
                            date: '2020/04/05',
                            receiverId: 'feng',
                            receiverName: 'jianbo',
                            receiverAvatar: '🌋',
                            receiverAvatarUrl: '',
                            senderId: 'test',
                            senderName: '路人甲',
                            senderAvatar: '🌋',
                            senderAvatarUrl: '',
                            isMe: false,

                        },
                        {
                            recordId: '00001',
                            t: "1",
                            msg: 'sadfasdfsadfasdfasdfsadf？',
                            date: '2020/04/05',
                            receiverId: 'test',
                            receiverName: '路人甲',
                            receiverAvatar: '🌋',
                            receiverAvatarUrl: '',
                            senderId: 'feng',
                            senderName: 'jianbo',
                            senderAvatar: '🌋',
                            senderAvatarUrl: '',
                            isMe: true,
                        },
                    ],
                },
                {
                    userId : '002',
                    userName: '路人乙',
                    avatar: '🌋',
                    avatarUrl: '',
                    date: '2020/04/14',
                    msg: '你不在么？',
                    isCurrent: false,
                    type: '1',  /*聊天类型：用户对用户*/
                    msgArr: [],
                },
                {
                    userId : '003',
                    userName: '路人丙',
                    avatar: '🌋',
                    avatarUrl: '',
                    date: '2020/04/14',
                    msg: '还是不在啊🍏？',
                    isCurrent: false,
                    type: '1',  /*聊天类型：用户对用户*/
                    msgArr: [],
                },
            ],
            currChatWindow: {   /*当前聊天窗口信息*/
                currentFriendUserId: '001',
                currentFriendType: '1', /*好友类型：用户*/
                currentFriendName: '路人甲',
                currentMessageContent: '🚝🚞🚋🚌🚍🚎🚏🚐🚑🚒🚓🚔🚕🚖🚗🚘🚚🚛🚜🚲⛽🚨🚥🚦🚧⚓⛵🚤🚢✈💺🚁🚟🚠🚡🚀🎑🗿🛂🛃🛄🛅\n' +
                    '                                <img src="/images/title.png" style="width: 50px; height: 25x;">',
                msgList: [
                    {
                        recordId: '00001',
                        t: "1",
                        msg: '你今天开心么？',
                        date: '2020/04/05',
                        receiverId: 'feng',
                        receiverName: 'jianbo',
                        receiverAvatar: '🌋',
                        receiverAvatarUrl: '',
                        senderId: 'test',
                        senderName: '路人甲',
                        senderAvatar: '🌋',
                        senderAvatarUrl: '',
                        isMe: false,

                    },
                    {
                        recordId: '00001',
                        t: "1",
                        msg: 'sadfasdfsadfasdfasdfsadf？',
                        date: '2020/04/05',
                        receiverId: 'test',
                        receiverName: '路人甲',
                        receiverAvatar: '🌋',
                        receiverAvatarUrl: '',
                        senderId: 'feng',
                        senderName: 'jianbo',
                        senderAvatar: '🌋',
                        senderAvatarUrl: '',
                        isMe: true,
                    },
                ],
            },
        },
        formSearchAuthUserRole: {},
        formSearchAuthRoleModule: {},
        formSearchAuthRole: {},
        formSearchAuthModule: {},
        formSearchAuthUserRoleModule: {},
        formSearchSysUser: {},
        formSearchMemberUser: {},
        formSearchCategory: {},
        formSearchCategoryType: {},
        formSearchDict: {},
        formSearchLangConf: {},
        formSearchNews: {},
        formSearchArticle: {},
        formSearchPartyDues: {},
        formSearchRes: {},
        formSearchResDl: {},
        formSearchBriefSendRecord: {},
        formSearchPrePartyMemeber: {},
        formdwjbxx:{},
        dwjbxxMain: false,
        dwjbxxMainAdd: false,   // 年度党员信息采集：是否呈现新增按钮
        adminOrgAdd:{
            label: '',
            isAdmin:true
            },
        nddyxxcjSelectOrg:'',
        dwjbxxBaseInfoType:'danger',
        dwjbxxPhoneType:'',
        dwjbxxLeaderType:'',
        dwjbxxViewType:'baseInfo',
        dwjbxxTreeData:[],
          dwjbxxProps: {
              children: 'children',
              label: 'orgName'
            },
        formdwjbxx2:{},
        formLeader:{},
        formDept:{},
        dialogShow: {},
        memberUsers: [],
        sysUsers: [],
        orgType:[],
        orgAddressRelation:[],
        elctType:[],
        leadTime:[],
        nddyxxcjTableData:[],
        changeOrgRelationAuth:[],
        isDelPartPersonAuth:[],
        belongArea:[],
        formReward:[],
        education:[],
        bachelor:[],
        positon:[],
        rewardName:[],
        orignRewardName:[],
        allowOrgLevel:[],
        authModules: [],
        leaderList:[
        ],
        firstPageZlxz:'dygl',
        articles: [],
        newsOperate: false,
        operateName: '操作',
        operateIcon: 'el-icon-setting',
        articleTypes: [],
        isSuperAdmin: false,
        resTableView:false,
        currUserReceiverBriefRecords: [],
        currUserReceiverNewsRecords: [],
        currUserReceiverBriefRecordsfirstPage: [],
        currUserReceiverXxjlRecordsfirstPage: [],
        currUserReceiverNewsRecordsfirstPage: [],
        faceMeetCode:'',
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
        typeDicts: [],      // 用于存储按type查询的字典信息
        resOtherTypes: [],  // 资源其他分类
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
        resArray: [],
        zlkloading: false,
        userUpperOrg:[],
        committeeOrg:[], //委员会架构
        resDlArray: [],
        toApprove: '',
        approveSuccess: '',
        approveFail: '',
        orignUpperOrg:[],
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
        briefReceiveUserIds: [],
        currNewsSendRecord: {},
        receiverUserData: [
            {
                id: 1,
                label: '中共上海市委书记应勇',
                title: '中共上海市委',
                children: [{
                    id: 4,
                    label: '浦东新区区委书记',
                    title: '新区区委书记',
                    children: [{
                        id: 9,
                        label: '区委副书记',
                        title: '副书记1'
                    }, {
                        id: 10,
                        label: '金桥镇书记',
                        title: '书记2',
                        children: [{
                            id: 29,
                            label: 'XX村长',
                            title: '李村长'
                        }, {
                            id: 30,
                            label: 'XX村委书记',
                            title: '村支书',

                        }]
                    }]
                }]
            }, {
                id: 2,
                label: '中共重庆市委书记陈敏尔',
                title: '重庆市委书记',
                children: [{
                    id: 5,
                    label: '唐良智',
                    title: '市长'
                }, {
                    id: 6,
                    label: '李明清',
                    title: '副市长'
                }]
            }
        ],
        /* WebSocket对象 */
        wsObj : {
            lockReconnect: false,
            webChatShow: false,
            webChatMainClass: 'webchat-main-default',
            url: "ws://" + window.location.hostname + ":" + window.location.port + "/api/socket/server",
            url2: "http://" + window.location.hostname + ":" + window.location.port + "/api/socket/sockjs/server"
        },
        allSysUsers: [],
        allSysUsersMap: {},
        currBrief: {},
        userRootOrg:[],
        currNotice: {},
        //uploadFileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
        uploadFileList: [],
        uploadFJList: [],
        uploadFJName: [],
        uploadFJUrl: [],
        continent: '',
        nation: '',
        rules: {},
        uploadData: {},
        uploadAccept: 'image/gif, image/jpeg, text/plain, application/pdf, image/png,' +
            'application/vnd.ms-powerpoint, ' +
            'application/rtf, text/rtf, ' +
            /*'application/vnd.ms-works, ' +*/
            'application/vnd.ms-excel, text/xml, application/xml,' +
            'aplication/zip, audio/3gpp, video/3gpp, audio/ac3, allpication/vnd.ms-asf, audio/basic, text/csv,' +
            /*'application/msword, ' +*/
            'application/xml-dtd, image/vnd.dwg, image/vnd.dxf, audio/mpeg,' +
            'audio/mp4, video/mp4, video/mpeg, application/vnd.ms-project, application/ogg, audio/ogg,' +
            'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, '+
            'application/vnd.openxmlformats-officedocument.spreadsheetml.template,'+
            'application/vnd.openxmlformats-officedocument.presentationml.template,' +
            'application/vnd.openxmlformats-officedocument.presentationml.slideshow,' +
            'application/vnd.openxmlformats-officedocument.presentationml.presentation,' +
            'application/vnd.openxmlformats-officedocument.presentationml.slide,' +
            'application/vnd.openxmlformats-officedocument.wordprocessingml.document,' +
            'application/vnd.openxmlformats-officedocument.wordprocessingml.template,' +
            'application/vnd.ms-excel.addin.macroEnabled.12,' +
            'application/vnd.ms-excel.sheet.binary.macroEnabled.12',
        /*发展党员*/
        fzdyBreadcrumbs: [
            /*{
                id: '1',
                title: '中国市政工程华北设计研究总院有限公司党委'
            },
            {
                id: '2',
                title: '第一分公司党委'
            },
            {
                id: '3',
                title: '工程一部党总支部'
            },
            {
                id: '4',
                title: '工程一部一支部'
            },*/
        ],
        fzdySteps: [
            {
                id: 1,
                title: '申请入党',
            },
            {
                id: 2,
                title: '入党积极分子的确定和培养',
            },
            {
                id: 3,
                title: '发展对象的确定和考察',
            },
            {
                id: 4,
                title: '预备党员的接收',
            },
            {
                id: 5,
                title: '预备党员的教育考察和转正',
            }
        ],

        fzdyPartyMembers: [
            {
                id: '0',
                name: '张荆楚',
                stage: 1,
            },
            {
                id: '1',
                name: '张荆楚',
                stage: 2,
            },
            {
                id: '2',
                name: '张荆楚',
                stage: 3,
            },
            {
                id: '3',
                name: '张荆楚',
                stage: 4,
            },
            {
                id: '4',
                name: '张荆楚',
                stage: 5,
            }
        ],

        editableTabsOptions: {

            editableTabsValue: 'firstPage',
            editableTabs: [
                {
                    title: '首页',
                    name: 'firstPage',
                    content: '',
                    closable: false,
                }],
            tabIndex: 2,
            activeName: 'first',
        },

        isIndeterminate: {
            roleModule: true,
            userRole: true,
            sysUserRole: true,
        },
        checkBoxAll: {
            roleModule: false,
            userRole: false,
            sysUserRole: false,
            article: false,
        },
        checkBoxOptions: {
            roleModule: [],
            userRole: [],
            sysUserRole: [],
            article: [],
        },
        /**
         * 复选中关联id
         */
        checkBoxRelationId: {
            roleModule: '',
            userRole: '',
            sysUserRole: '',
        },
        chatEmojis: {},

        pager: {
        	orgUser: {
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
                totalCount: 0,
            },
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
                totalCount: 0,
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
                totalCount: 0,
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
                totalCount: 0,
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
                totalCount: 0,
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
                totalCount: 0,
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
                totalCount: 0,
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
                totalCount: 0,
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
                totalCount: 0,
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
                totalCount: 0,
            },

            briefSendRecord: {
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
                totalCount: 0,
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
                totalCount: 0,
            },

            res: {
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
                totalCount: 0,
            },

            resDl: {
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
                totalCount: 0,
            },
        },
        defaultProps: {
            children: 'children',
            label: 'label'
        },
        orgCommitteeLoading: false,
        isRoleAdmin: null,
        showChat: false,
        chatSrc: `http://localhost:3006/#/welcome`

    },
    computed:{
    	homePageStyle (){
    		return {
    			height: `calc(100% - 10px)`,
    		}
    	},
    	
    	
    },
    methods: {
    	handleOpenChat () {
    		//let that = this;
		//let _src = 'http://www.awycjcdj.com:8090/a.html';
		//that.chatSrc = `${_src}?data=` + that.currentUserStr;
		//this.showChat = true;
		let that = this;
		let _src = that.chatConfig.web.url || '';
		that.chatSrc = `${_src}?data=` + that.currentUserStr;
		let chatWin = window.open(that.chatSrc,'','width=500,height=500,left=200,toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no',true);
		chatWin.focus();

    	},
    	handleCloseChat () {
    		this.showChat = false;
    	},
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        handleNavMenu(tab, event, indexPath) {    //处理菜单选择
            let that = this;
            console.log("handleNavMenu:", tab, event, indexPath);
            if (tab != 'firstPage') {
                let _m2 = that.userOwnedModules.find(function(m){ return m.moduleCode == tab});
                console.log('m2 ==>', _m2);
                /*
                let tmpModules = that.userOwnedModules.filter(_module => _module.moduleCode == tab);
                console.log('tmpModules', that.userOwnedModules, tmpModules);
                 */
                if (_m2 && _m2.moduleCode) {
                    that.handleAddTab2(_m2);
                }
            }
            else {
                that.editableTabsOptions.editableTabsValue = 'firstPage';
            }
            that.showContent = tab;
        },
        resetForm(formName) {
            console.log('resetForm',formName);
            let that = this;
            that.$refs[formName].resetFields();
            if(that.showContent === 'formArticle') {
                that.formArticle = {
                    isEdit : false,
                    recordId: '',
                    categoryId: '53c34dec-7447-4bbc-9ff3-af0f0686b07f', //简报
                    categoryName: '',
                    title: '',
                    intro: '',
                    content: '',
                    tags: '',
                    source: '',
                    authorName: '',
                    sourceSite: '',
                    creatorId: '',
                    creatorName: '',
                    updatorId: '',
                    updatorName: '',
                    updateTime: '',
                    recordTime: '',
                    sendType: '1',
                    receiverIds: [],
                    receiveUsers: [],
                    receiverIdArrStr: '',
                };
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
            if (formName == 'formModifyPwd') {
                let __password = that.formModifyPwd.password.replace(/(^\s*)|(\s*$)/g, "");
                let __passwordOrgi = that.formModifyPwd.passwordOrgi.replace(/(^\s*)|(\s*$)/g, "");
                if (__password == '') {
                    that.$message.error('请填写\'密码\'');
                    return false;
                }
                if (__password.length < 6 || __password.length > 128) {
                    that.$message.error('\'密码\'长度长度在6~128之间');
                    return false;
                }
                if (__passwordOrgi == '') {
                    that.$message.error('请输入\'重复密码\'');
                    return false;
                }
                if (__passwordOrgi.length < 6 || __passwordOrgi.length > 128) {
                    that.$message.error('\'重复密码\'长度在6~128之间');
                    return false;
                }
                if (__password != __passwordOrgi) {
                    that.$message.error('两次输入密码不一致');
                    return false;
                }
            }
            if (formName == 'formArticle') {
                that.formArticle.content = that.ueditors.article.getContent();
                console.log('that.currentArticleFormTitle', that.currentArticleFormTitle);
                console.log('that.formArticle.content', that.formArticle.content);
                console.log('that.ueditors.article.getContent()', that.ueditors.article.getContent());
                let __title = that.formArticle.title.replace(/(^\s*)|(\s*$)/g, "");
                let __content = that.formArticle.content.replace(/(^\s*)|(\s*$)/g, "");
                let __receiverIds = that.formArticle.receiverIds || [];
                if (that.currentArticleFormTitle === '简报' || that.currentArticleFormTitle === '学习交流') {
                    if (__receiverIds.length === 0) {
                        that.$message.error('请选择接收人');
                        return false;
                    }
                }
                if (__title == '') {
                    that.$message.error('请填写主题');
                    return false;
                }
                if (__title.length < 3 || __title.length > 2000) {
                    that.$message.error('主题长度在3~2000之间');
                    return false;
                }
                if (__content == '') {
                    that.$message.error('请填写正文');
                    return false;
                }
                if(__content.length < 3) {
                    that.$message.error('正文不能少于三个字符');
                    return false;
                }
            }
               		
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
                            if (formName == 'formArticle') {    //文章特殊处理
                                that.submitArticle();
                                that.briefReceiveUserIds = [];
                                that.resetForm(formName);
                            }
                            else {
                                if (valid) {
                                    // 表单验证通过之后的操作
                                    switch (formName) {
                                        case 'formAuthRole' : that.sumbitAuthRole(); break;
                                        case 'formAuthModule': that.submitAuthModule(); break;
                                        case 'formUser': that.submitUser(false); break;
                                        case 'formSysUser': that.submitUser(true); break;

                                        case 'formCategoryType': that.submitCategoryType(); break;
                                        case 'formCategory': that.submitCategory(); break;

                                        case 'formLangConf': that.submitLangConf(); break;
                                        case 'formdwjbxx':
                                        	that.$refs['formdwjbxx2'].validate((valid) => {
                                        		if(valid){
                                        			if(that.dwjbxxFormValidate()){
                                        				that.submitDwjbxx();
                                        			}else{
                                        				return false;
                                        			}
                                        		}else {
                                                    this.$message.error('提交失败,请按要求填写表单内容');
                                                    return false;
                                                }
                                        	});

                                            break;
                                        case 'formNews': that.submitNews(); break;
                                        case 'formPartyDues': that.submitPartyDues(); break;
                                        case 'formRes': that.submitRes(); break;
                                        case 'formnddyxxcj': that.submitNddyxxcj();break;
                                        case 'formModifyPwd': that.submitModifyPwd(); break;
                                        default: break;
                                    }
                                    //提交成功之后
//                                    if(formName != 'formdwjbxx' && formName != 'formnddyxxcj'){
//                                        that.resetForm(formName);
//                                    }
                                } else {
                                    this.$message.error('提交失败,请按要求填写表单内容');
                                    return false;
                                }
                            }
                        });
                    }
                }
            });

                
            
            //勿删，特意写下此句
            console.log('submitForm-formName,',formName,new Date().getTime());
        },
        dwjbxxFormValidate(){
        	let that = this;
        	if(that.formdwjbxx.orgName == null || that.formdwjbxx.orgName == ''){
        		this.$message.error('提交失败,请填写 基本信息-->党组织名称');
        		return false;
        	}
        	if(that.formdwjbxx.foundTime == null || that.formdwjbxx.foundTime == ''){
        		this.$message.error('提交失败,请选择 基本信息-->批准建立党组织日期');
        		return false;
        	}
        	if(that.formdwjbxx.orgType == null || that.formdwjbxx.orgType == ''){
        		this.$message.error('提交失败,请选择 基本信息-->党组织类型');
        		return false;
        	}
        	if(that.formdwjbxx.orgAddressRelation == null || that.formdwjbxx.orgAddressRelation == ''){
        		this.$message.error('提交失败,请选择 基本信息-->党组织属地关系');
        		return false;
        	}
        	if(that.formdwjbxx.elctType == null || that.formdwjbxx.elctType == ''){
        		this.$message.error('提交失败,请选择 基本信息-->选举方式');
        		return false;
        	}
        	if(that.formdwjbxx.elctType == null || that.formdwjbxx.elctType == ''){
        		this.$message.error('提交失败,请选择 基本信息-->选举方式');
        		return false;
        	}
        	if(that.formdwjbxx.currentLeaderTime == null || that.formdwjbxx.currentLeaderTime == ''){
        		this.$message.error('提交失败,请选择 基本信息-->本届班子当选日期');
        		return false;
        	}
        	if(that.formdwjbxx.changeOrgRelationAuth == null || that.formdwjbxx.changeOrgRelationAuth == ''){
        		this.$message.error('提交失败,请选择 基本信息-->转接组织关系权限');
        		return false;
        	}
        	if(that.formdwjbxx2.concatPersion == null || that.formdwjbxx2.concatPersion == ''){
        		this.$message.error('提交失败,请填写 联系方式-->党组织联系人');
        		return false;
        	}
        	if(that.formdwjbxx2.phone == null || that.formdwjbxx2.phone == ''){
        		this.$message.error('提交失败,请填写 联系方式-->党组织联系人手机号');
        		return false;
        	}
        	if(that.formdwjbxx2.orgJobPhone == null || that.formdwjbxx2.orgJobPhone == ''){
        		this.$message.error('提交失败,请填写 联系方式-->党组织办公电话');
        		return false;
        	}
        	
        	return true;
        },
        getOrgTypeValue(orgType1){
        	let list = this.orgType.filter(x=>x.dictId == orgType1);
        	if(list && list.length > 0){
        		return list[0].dictName;
        	}
        },
        getGenderValue(genderId){
        	let list = this.gender.filter(x=>x.value == genderId);
        	if(list && list.length > 0){
        		return list[0].label;
        	}
        },
        operateNews(){
        	let that = this;
        	that.newsOperate = !that.newsOperate;
        	if(that.newsOperate){
        		that.operateName = '完成';
        		that.operateIcon = 'el-icon-check';
        	}else{
        		that.operateName = '操作';
        		that.operateIcon = 'el-icon-setting';
        	}
        },
        deleteNews(recordId){
        	let that = this;
        	let params = new URLSearchParams();
            params.append('id', recordId || '');
            that.newsLoading = true;
        	axios.post("/api/news/deleteById", params)
            .then(function(response){
            	that.handleResponse(response);
                let responseCode = parseInt(response.data.code);
                if(responseCode === 200){
                	that.$message({
                        message: '删除成功',
                        type: 'success'
                    });
                	that.searchForm('formSearchNews');
                	that.newsLoading = false;
                }else{
                	that.$message.error('删除失败');
                	that.newsLoading = false;
                }
            }).catch(function(err){
            	that.$message.error('删除失败');
            	that.newsLoading = false;
            });
        },
        deleteArtcle(recordId){
        	let that = this;
        	let params = new URLSearchParams();
            params.append('recordId', recordId || '');
            that.newsLoading = true;
        	axios.post("/api/article/deleteArticleById", params)
            .then(function(response){
            	that.handleResponse(response);
                let responseCode = parseInt(response.data.code);
                if(parseInt(responseCode) === 200){
                	that.$message({
                        message: '删除成功',
                        type: 'success'
                    });
                	that.receiveArtcleType='';
            		that.sendArtcleType='info';
            		//that.currentArticleFormTitle = '简报';
                    that.formSearchArticle.isBrief = true;
                    that.formSearchArticle.sendType = '1';
                    //that.formSearchArticle.categoryId = '53c34dec-7447-4bbc-9ff3-af0f0686b07f';
                    //that.loadArticles('',1, that.pager.article.pageSize);
                    that.currAction = 'append';
                    //that.def_menu_id = 'articles';
                    if(that.currentArticleFormTitle == '简报'){
                    	that.loadCurrUserSendBriefRecord(that.formSearchBriefSendRecord.key, 1, that.pager.briefSendRecord.pageSize,null);
                    }
                    if(that.currentArticleFormTitle == '学习交流'){
                    	that.loadCurrUserSendBriefRecord(that.formSearchBriefSendRecord.key, 1, that.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
                    }
                	that.newsLoading = false;
                }else{
                	that.$message.error('删除失败');
                	that.newsLoading = false;
                }
            }).catch(function(err){
            	that.$message.error('删除失败');
            	that.newsLoading = false;
            });
        },
        approveXXJL(recordId,approve){

        	let that = this;
        	let params = new URLSearchParams();
            params.append('briefId', recordId || '');
            params.append('approveType', approve);
            that.newsLoading = true;
        	axios.post("/api/article/xxjlApprove", params)
            .then(function(response){
            	that.handleResponse(response);
                let responseCode = parseInt(response.data.code);
                if(parseInt(responseCode) === 200){
                	that.$message({
                        message: '审批成功',
                        type: 'success'
                    });
                    that.formSearchArticle.isBrief = true;
                    that.formSearchArticle.sendType = '1';
                    that.currAction = 'append';
                    
                    that.loadCurrUserSendBriefRecord(that.formSearchBriefSendRecord.key, that.pager.briefSendRecord.currentPage, that.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
                	that.newsLoading = false;
                }else{
                	that.$message.error('审批失败');
                	that.newsLoading = false;
                }
            }).catch(function(err){
            	that.$message.error('审批失败');
            	that.newsLoading = false;
            });
        
        },
        getNationValue(nationId){
        	let list = this.nation.filter(x=>x.dictId == nationId);
        	if(list && list.length > 0){
        		return list[0].dictName;
        	}
        },
        getEducationValue(educationId){
        	let list = this.education.filter(x=>x.dictId == educationId);
        	if(list && list.length > 0){
        		return list[0].dictName;
        	}
        },
        getOrgValue(orgid){
        	
        	let list = this.orignUpperOrg.filter(x=>x.orgId == orgid);
        	if(list && list.length > 0){
        		return list[0].orgName;
        	}
        },
        getBachelorValue(bachelorId){
        	let list = this.bachelor.filter(x=>x.dictId == bachelorId);
        	if(list && list.length > 0){
        		return list[0].dictName;
        	}
        },
        getPositionValue(positionId){
        	let list = this.positon.filter(x=>x.dictId == positionId);
        	if(list && list.length > 0){
        		return list[0].dictName;
        	}
        },
        getRewardNameValue(rewardId){
        	let list = this.orignRewardName.filter(x=>x.nodeId == rewardId);
        	if(list && list.length > 0){
        		return list[0].nodeName;
        	}
        },
        getAllowOrgLevelValue(allowOrgLevel){
        	let list = this.allowOrgLevel.filter(x=>x.dictId == allowOrgLevel);
        	if(list && list.length > 0){
        		return list[0].dictName;
        	}
        },
        getAllowOrgLevelValue(level){
        	let list = this.allowOrgLevel.filter(x=>x.dictId == level);
        	if(list && list.length > 0){
        		return list[0].dictName;
        	}
        },
        searchForm(formName) {
            let that = this;
            switch (formName) {
                case 'formSearchAuthModule':
                    that.loadAuthModules(that.formSearchAuthModule.moduleName,1,that.pager.authModule.pageSize);
                    break;
                case 'formSearchSysUser':
                    that.loadSysUsers(that.formSearchSysUser.key, 1, that.pager.sysUser.pageSize);
                    break;
                case 'formSearchMemberUser':
                    that.loadMemberUsers(that.formSearchMemberUser.key, that.formSearchMemberUser.typeId,1,that.pager.user.pageSize);
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
                case 'nddyxxcj':
                	that.pager.orgUser.currentPage = 1;
                	that.loadNddyxxcj(1, that.pager.orgUser.pageSize);
                	break;
                case 'formSearchNews':
                    that.loadNews(that.formSearchNews.title, 1, that.pager.news.pageSize);
                    break;
                case 'formSearchArticle':
                    that.loadArticles(that.formSearchArticle.key, 1, that.pager.article.pageSize);
                    break;
                case 'formSearchPartyDues':
                    that.loadPartyDues(that.formSearchPartyDues.key, 1, that.pager.partyDues.pageSize);
                    break;
                case 'formSearchRes':
                    that.loadResList(that.formSearchRes.key, 1, that.pager.res.pageSize);
                    break;
            }
        },
        setDyxxYear(){
        	let that = this;
        	let date = new Date();
        	let currentYear = date.getFullYear();
        	that.dyxxyear.year = currentYear;
        	that.yearList=[];
        	that.yearList.push(currentYear);
        	/*that.yearList.push(currentYear-1);
        	that.yearList.push(currentYear-2);
        	that.yearList.push(currentYear-3);*/
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
                    break;
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
            let params = new URLSearchParams();
            let userId = '';
            let operName = '新增';
            if(that.currAction !== 'append') {
                operName = '更新';
            }

            if (isAdmin) {
                userId = that.formSysUser.id || '';
                params.append('userId', userId);
                params.append('userType', '1');
                params.append('userName', that.formSysUser.name || '');
                params.append('userNickname', that.formSysUser.nickname || '');
                params.append('userPwd', that.formSysUser.password || '');
                params.append('oldPassword', that.formSysUser.oldPassword || '');
                params.append('passwordOrgi', that.formSysUser.passwordOrgi || '');
                params.append('langConfId', '');
                params.append('orgId', that.formSysUser.orgId || '');
                params.append('mail',that.formSysUser.mail || '');
                params.append('phone',that.formSysUser.phone || '');
               
            }
            else {
                userId = that.formUser.id || '';
                params.append('userId', userId);
                params.append('userType', '3');
                params.append('userName', that.formUser.name || '');
                params.append('userNickname', that.formUser.nickname || '');
                params.append('userPwd', that.formUser.password || '');
                params.append('oldPassword', that.formUser.oldPassword || '');
                params.append('passwordOrgi', that.formUser.passwordOrgi || '');
                params.append('langConfId','');
            }
            that.fullscreenLoading = true;
            axios.post("/api/user/saveUserBaseInfo", params)
                .then(function(response){
                	that.handleResponse(response);
                    let responseCode = parseInt(response.data.code);
                    if(responseCode === 200){
                        if (isAdmin) {
                            //that.getAllSysUsers();
                            that.getAllSysUsersMap();
                            that.dialogShow.sysUser = false;
                            that.loadSysUsers('', 1, that.pager.sysUser.pageSize);
                        }
                        else {
                            that.dialogShow.memberUser = false;
                            that.loadMemberUsers('', '3', 1, that.pager.user.pageSize);
                        }

                        that.$message({
                            message: operName + '用户信息成功!',
                            type: 'success'
                        });
                    }
                    else if (responseCode == 555) {
                        that.$message.error( response.data.data +  '重复');
                    }
                    else if (responseCode === 411) {
                        let _msg = response.data.msg;
                        if (_msg === 'REQUEST_PARAM_ERROR') {
                            //this.$message.error('请求参数异常');
                            that.$message.error('请求参数异常');
                        }
                        else {
                            that.$message.error( response.data.msg);
                        }
                    }else{
                    	that.$message.error(response.data.msg);
                    }
                    that.fullscreenLoading = false;
                }).catch(function(err){
                	that.fullscreenLoading = false;
                console.warn(err);
            });
        },

        /**
         * 修改密码
         */
        submitModifyPwd() {
            let that = this;
            let params = new URLSearchParams();
            params.append('userId', that.currentUser.userId || '');
            params.append('password', that.formModifyPwd.password || '');
            //params.append('oldPassword', that.formModifyPwd.oldPassword || '');
            params.append('passwordOrgi', that.formModifyPwd.passwordOrgi || '');

            axios.post("/api/user/modifyPwd", params)
                .then(function(response){
                	that.handleResponse(response);
                    let responseCode = parseInt(response.data.code);
                    if(responseCode === 200){
                        that.dialogShow.modifyPwd = false;
                        that.$message({
                            message: '密码修改成功!',
                            type: 'success'
                        });
                    }
                    else if (responseCode === 411) {
                        let _msg = response.data.msg;
                        if (_msg === 'REQUEST_PARAM_ERROR') {
                            //this.$message.error('请求参数异常');
                            that.$message.error('请求参数异常');
                        }
                        else {
                            that.$message.error( response.data.msg);
                        }
                    }
                }).catch(function(err){
                    that.$message.error( '密码修改失败');
                console.warn(err);
            });
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
            params.append('moduleLogo', that.formAuthModule.moduleLogo || '');
            params.append('isMenu', that.formAuthModule.isMenu || 0);
            if(that.currAction === 'edit') {
                operName = '修改';
                params.append('moduleId',that.formAuthModule.moduleId);
            }
            axios.post("/api/user/saveModule",params)
                .then(function(response){
                	that.handleResponse(response);
                    that.responseMessageHandler(response, '模块信息', operName, function() {
                        that.dialogShow.authModule = false;
                        that.pager.authModule.currentPage = 1;
                        that.loadAuthModules('',1,that.pager.authModule.pageSize);
                    });
                }).catch(function(err){
                console.warn(err);
            });
        },
        addYear(){
        	if(this.formnddyxxcjyear.year != null && this.formnddyxxcjyear.year != ''){
        		this.yearList.push(this.formnddyxxcjyear.year);
        	}
        	this.dialogShow.nddyxxcjYear = false;
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
                	that.handleResponse(response);
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
            let operName = '新增';
            let sendType = that.formArticle.sendType || '';    // 默认为简报
            let receiverIds = that.formArticle.receiverIds || [];
            console.log('(submitArticle)receiverIds', receiverIds);
            // console.log(' submitArticle => (that.formArticle) => ',  that.formArticle)
            params.append('typeId',that.formArticle.typeId || '');
            params.append('categoryId',that.formArticle.categoryId || '');
            params.append('title', that.formArticle.title || '');
            params.append('intro',that.formArticle.intro || '');
            params.append('content',that.ueditors.article.getContent() || '');
            params.append('tags',that.formArticle.tags || '');
            params.append('authorName',that.formArticle.authorName || '');
            params.append('source',that.formArticle.source || '');
            params.append('sourceSite',that.formArticle.sourceSite || '');
            if(that.isSuperAdmin){
            	params.append('superAdmin','1');
            }else{
            	params.append('superAdmin','0');
            }
            if (that.currentArticleFormTitle === '简报' || that.currentArticleFormTitle === '学习交流') {
                params.append('sendType', sendType);
                params.append('receiverIds', receiverIds);

            }
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

            let articleType = sendType == '1' ? '简报' : '文章';

            axios.post("/api/article/save", params)
                .then(function(response){
                	that.handleResponse(response);
                    let responseCode = parseInt(response.data.code);
                    if(responseCode === 200){
                        that.dialogShow.article = false;
                        if (that.currentArticleFormTitle === '简报') {
                            that.editableTabsOptions.editableTabsValue = 'articles';
                            that.def_menu_id = 'articles';
                            that.showContent = 'articles';
                            that.$refs['menuRef'].activeIndex = 'articles';
                            that.briefReceiveUserIds = [];
                            that.pager.briefSendRecord.currentPage = 1;
                            if(that.sendArtcleType =='info'){
                            	that.loadCurrUserSendBriefRecord('', 1, that.pager.briefSendRecord.pageSize);
                            }else{
                            	that.loadCurrUserReceiverBriefRecord('', 1, that.pager.briefSendRecord.pageSize);
                            }
                            
                            that.$forceUpdate();
                            that.$message({
                                message: articleType + operName + '成功!',
                                type: 'success'
                            });
                        }else if(that.currentArticleFormTitle === '学习交流'){
                        	that.receiveArtcleType='info';
                    		that.sendArtcleType='';
                    		that.toApprove='',
                    		that.approveSuccess='',
                    		that.approveFail='',
                    		that.newsOperate=false;
                    		that.operateName = '操作';
                    		that.operateIcon = 'el-icon-setting';
                    		that.currentArticleFormTitle = '学习交流';
                            that.formSearchArticle.isBrief = true;
                            that.formSearchArticle.sendType = '1';
                            that.formSearchArticle.categoryId = '63c34dec-7447-4bbc-9ff3-af0f0686b07f';
                        	that.editableTabsOptions.editableTabsValue = 'articles';
                            that.def_menu_id = 'xxjl';
                            that.showContent = 'xxjl';
                            that.$refs['menuRef'].activeIndex = 'xxjl';
                            that.briefReceiveUserIds = [];
                            that.pager.briefSendRecord.currentPage = 1;
                            if(that.receiveArtcleType =='info'){
                            	that.loadCurrUserReceiverBriefRecord('', 1, that.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');

                            }else{
                            	that.loadCurrUserSendBriefRecord('', 1, that.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');

                            }
                            
                            that.$forceUpdate();
                            that.$message({
                                message: articleType + operName + '成功!',
                                type: 'success'
                            });
                        }
                        else {
                            that.editableTabsOptions.editableTabsValue = 'articleManagement';
                            that.def_menu_id = 'articleManagement';
                            that.showContent = 'articleManagement';
                            that.$refs['menuRef'].activeIndex = 'articleManagement';
                            that.briefReceiveUserIds = [];
                            that.pager.article.currentPage = 1;
                            that.loadArticles('',1, that.pager.article.pageSize);
                            that.$forceUpdate();
                            that.$message({
                                message: articleType + operName + '成功!',
                                type: 'success'
                            });
                        }
                    }
                    else if (responseCode === 555) {
                        that.$message.error('主题重复');
                        return false;
                    }
                    else if (responseCode === 411) {
                        let _msg = response.data.msg;
                        if (_msg === 'REQUEST_PARAM_ERROR') {
                            //this.$message.error('请求参数异常');
                            that.$message.error('请求参数异常');
                            return false;
                        }
                        else {
                            that.$message.error( response.data.msg);
                            return false;
                        }
                    }
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
                	that.handleResponse(response);
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
         * 党员信息提交
         */
        submitNddyxxcj(){
        	let that = this;
        	let params = new URLSearchParams();
        	
        	if(that.formnddyxxcj.userId != null){
            	params.append('userId',that.formnddyxxcj.userId);
        	}
        	if(that.formnddyxxcj.orgId != null){
            	params.append('orgId',that.formnddyxxcj.orgId);
        	}
        	if(that.formnddyxxcj.userName != null){
            	params.append('userName',that.formnddyxxcj.userName);
        	}
        	if(that.formnddyxxcj.gender != null){
            	params.append('gender',that.formnddyxxcj.gender);
        	}
        	if(that.formnddyxxcj.birthTime != null){
            	params.append('birthTime',that.formnddyxxcj.birthTime);
        	}
        	if(that.formnddyxxcj.nation != null){
            	params.append('nation',that.formnddyxxcj.nation);
        	}
        	if(that.formnddyxxcj.joinPartyTime != null){
            	params.append('joinPartyTime',that.formnddyxxcj.joinPartyTime);
        	}
        	if(that.formnddyxxcj.turnRightTime != null){
            	params.append('turnRightTime',that.formnddyxxcj.turnRightTime);
        	}
        	if(that.formnddyxxcj.hometown != null){
            	params.append('hometown',that.formnddyxxcj.hometown);
        	}
        	if(that.formnddyxxcj.bachelor != null){
            	params.append('bachelor',that.formnddyxxcj.bachelor);
        	}
        	
        	if(that.formnddyxxcj.education != null){
            	params.append('education',that.formnddyxxcj.education);
        	}
        	if(that.formnddyxxcj.officeNumber != null){
            	params.append('officeNumber',that.formnddyxxcj.officeNumber);
        	}
        	if(that.formnddyxxcj.liveAddress != null){
            	params.append('liveAddress',that.formnddyxxcj.liveAddress);
        	}
        	if(that.formnddyxxcj.mail != null){
            	params.append('mail',that.formnddyxxcj.mail);
        	}
        	if(that.formnddyxxcj.imageUrl != null){
            	params.append('imageUrl',that.formnddyxxcj.imageUrl);
        	}
        	if(that.formnddyxxcj.phone != null){
            	params.append('phone',that.formnddyxxcj.phone);
        	}
        	if(that.formnddyxxcj.leader != null){
            	params.append('leader',that.formnddyxxcj.leader);
        	}
        	if(that.formnddyxxcj.idCard != null){
            	params.append('idCard',that.formnddyxxcj.idCard);
        	}
        	that.fullscreenLoading = true;
        	axios.post("/api/org/orgUserOpreate",params)
    		.then(function(response){
    			that.handleResponse(response);
    			if(parseInt(response.data.code) === 200){
    				that.dialogShow.nddyxxcj =false;
    				that.loadNddyxxcj(that.pager.orgUser.currentPage,that.pager.orgUser.pageSize);
                    that.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                }else if(parseInt(response.data.code) === 2000){
                	that.$message.error(response.data.msg);
                }
    			else{
                	that.$message.error("提交失败");
                }
    			that.fullscreenLoading = false;
    		}).catch(function(err){
    			that.fullscreenLoading = false;
    			that.$message.error("提交失败,请联系管理员");
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
        	if(that.formdwjbxx2.phone != null){
        		params.append('phone',that.formdwjbxx2.phone);
        	}
        	if(that.formdwjbxx.upperOrg != null){
        		params.append('upperOrg',that.formdwjbxx.upperOrg);
        	}
        	if(that.formdwjbxx.foundTime != null){
        		params.append('foundTime',that.formdwjbxx.foundTime);
        	}
        	if(that.formdwjbxx2.transCode != null){
        		params.append('transCode',that.formdwjbxx2.transCode);
        	}
        	if(that.formdwjbxx2.fixPhone != null){
        		params.append('fixPhone',that.formdwjbxx2.fixPhone);
        	}
        	if(that.formdwjbxx2.address != null){
        		params.append('address',that.formdwjbxx2.address);
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
        	if(that.formdwjbxx2.concatPersion != null){
        		params.append('concatPersion',that.formdwjbxx2.concatPersion);
        	}
        	if(that.formdwjbxx2.orgJobPhone != null){
        		params.append('orgJobPhone',that.formdwjbxx2.orgJobPhone);
        	}
        	if(that.formdwjbxx2.belongArea != null){
        		params.append('belongArea',that.formdwjbxx2.belongArea);
        	}
        	if(that.formdwjbxx.rootOrg != null){
        		params.append('rootOrg',that.formdwjbxx.rootOrg);
        	}
        	params.append('leaderDetails',JSON.stringify(that.leaderList));
        	params.append('rewardDetails',JSON.stringify(that.rewardList));
        	params.append('deptDetails',JSON.stringify(that.deptInfoList));
        	that.fullscreenLoading = true;
        	axios.post("/api/org/orgOpreate",params)
        		.then(function(response){
        			that.handleResponse(response);
        			if(parseInt(response.data.code) === 200){
        				that.dialogShow.dwjbxx =false;
            			//that.getUpperOrg();
        				axios.get("/api/org/getOrgDetailById",{params:{
			    			orgId: response.data.result
			            }})
			            .then(function(response){/*成功*/
			            	that.handleResponse(response);
			                let data = response.data;
			                if(parseInt(data.code) === 200) {
			                	that.formdwjbxx = response.data.result;
			                	that.formdwjbxx2 = response.data.result;
			                	
			                	that.dwjbxxDialog.title = that.formdwjbxx.orgName;
			                	
			                }
			                that.fullscreenLoading = false;
			            })
			            .catch(function(err){/*异常*/
			                console.log(err);
			                that.fullscreenLoading = false;
			            });
			    		//领导班子
			    		axios.get("/api/org/getOrgLeaderList",{params:{
			    			orgId: response.data.result
			            }})
			            .then(function(response){/*成功*/
			            	that.handleResponse(response);
			                let data = response.data;
			                if(parseInt(data.code) === 200) {
			                	that.leaderList = response.data.result
			                }
			            })
			            .catch(function(err){/*异常*/
			            });
            			that.loadDwjbxx();
                        that.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                    }else{
                    	that.loading.flag = false;
                    	this.$message.error("提交失败");
                    }
        			that.fullscreenLoading = false;
        		}).catch(function(err){
        			that.fullscreenLoading = false;
        			that.$message.error("提交失败,请联系管理员");
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
                that.formNews.receiverId = that.formNews.receiveUserIds.join(",");
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
            let fileUrl = '';
            let fileName = '';
            if(that.uploadFJUrl != null && that.uploadFJUrl.length > 0){
            	fileUrl = that.uploadFJUrl.join(',');
            }
            if(that.uploadFJName != null && that.uploadFJName.length > 0){
            	fileName = that.uploadFJName.join(',');
            }
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
                "fileUrl" : fileUrl,
                "fileName": fileName
            };
            if(that.currAction === 'edit') {
                operName = '修改';
                // params.append('recordId',that.formNews.recordId || '');
            }
            that.fullscreenLoading=true;
            axios.post("/api/news/save", _data, {
                headers: {
                    "Content-type": "application/json;charset=utf-8"
                }
                })
                .then(function(response){
                	that.handleResponse(response);
                    that.responseMessageHandler(response, '消息', operName, function() {
                        that.dialogShow.news = false;
                        that.pager.news.currentPage = 1;
                        that.loadNews('',1, that.pager.news.pageSize);
                        // 再次加载首页通知消息(如果消息发给自己)
                        that.loadCurrUserNewsOfFirstPage();
                    });
                    that.fullscreenLoading=false;
                }).catch(function(err){
                	that.fullscreenLoading=false;
                console.warn(err);
            });
        },
        getOrgIdByUserId(){
        	let that = this;
        	axios.get("/api/org/getOrgIdByUserId")
            .then(function(response){/*成功*/
            	that.handleResponse(response);
                let data = response.data;
                if(parseInt(response.data.code) == 200 ){
                    that.currentUserOrgId = response.data.result;
                }
            })
            .catch(function(err){/*异常*/
                console.log(err);
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
                	that.handleResponse(response);
                    that.responseMessageHandler(response, '党费缴纳', operName, function() {
                        that.dialogShow.partyDues = false;
                        that.pager.partyDues.currentPage = 1;
                        that.loadPartyDues('',1,that.pager.partyDues.pageSize);
                    });
                }).catch(function(err){
                console.warn(err);
            });
        },

        /**
         * 资源信息提交
         */
        submitRes() {
            let that = this;
            let operName = '添加';
            that.orgCommitteeLoading = true;
            /*
            let params = new URLSearchParams();
            params.append("assId", that.formRes.assId);
            params.append("assTypeId", that.formRes.assTypeId)
            params.append('typeId', that.formRes.typeId);
            params.append('publishTime', that.formRes.publishTime);
            params.append('resName',that.formRes.resName || '');
            params.append('originalName',that.formRes.originalName || '');
             */
            let _data = {
                "recordId" : that.formRes.recordId || '',
                "assId" : that.formRes.assId || '',
                "assTypeId" : that.formRes.assTypeId || '',
                "typeId" : that.formRes.typeId || '',
                "publishTime" : that.formRes.publishTime || '',
                "resName" : that.formRes.resName || '',
                "originalName" : that.formRes.originalName || '',
                "resSize" : that.formRes.resSize || '0',
                "remark" : that.formRes.remark || '',
                "announcerId" : that.formRes.announcerId || '',
                "resAuthor" : that.formRes.resAuthor || '',
                "resSrc" : that.formRes.resSrc || '',
                "resIntro" : that.formRes.resIntro || '',
                "resDesc" : that.formRes.resDesc || '',
                "resTags" : that.formRes.resTags || '',
                "accessUrl" : that.formRes.accessUrl || '',
                "currName" : that.formRes.currName || '',
                "orgId" : that.formRes.orgId || '',
                "recordFlag": that.formRes.recordFlag || '1',
                "attaContent" : that.formRes.attaContent || '',
            };
            if(that.currAction === 'edit') {
                operName = '修改';
                // params.append('recordId',that.formRes.recordId || '');
            }
            axios.post("/api/res/save",_data, {
                headers: {
                    "Content-type": "application/json;charset=utf-8"
                }
            })
            .then(function(response){
	            	that.handleResponse(response);
	                that.responseMessageHandler(response, '资源信息', operName, function() {
	                    that.dialogShow.res = false;
	                    that.pager.res.currentPage = 1;
	                    that.loadResList('',that.pager.res.currentPage,that.pager.res.pageSize);
	                });
	                that.orgCommitteeLoading = false;
	            }).catch(function(err){
	            console.warn(err);
	            that.orgCommitteeLoading = false;
	        });
        },
        goToFaceMeet(){
        	if(this.faceMeetCode != '' && this.faceMeetCode != null){
        		let url = "https://www.awycjcdj.com:8443/" + this.faceMeetCode;
        		window.open(url);
        	}
        	this.faceMeetCode = '';
        },
        createFaceMeet(){
        	
			let url = "https://www.awycjcdj.com:8443/";
			window.open(url);
        	
        	
        },
        /**
         * 更新资源附件信息
         */
        uploadResAttachmentInfo(entry) {
            let that = this;
            let params = new URLSearchParams();
            params.append('recordId',entry.recordId || '');
            params.append("originalName", entry.originalName || '');
            params.append("currName", entry.currName || '')
            params.append('accessUrl', entry.accessUrl || '');
            params.append('resSize', entry.resSize || '0');
            params.append('attaContent', entry.attaContent || '');
            axios.post("/api/res/uploadAttachmentInfo", params)
                .then(function(response){
                	that.handleResponse(response);
                    if(parseInt(response.data.code) === 200){
                        that.dialogShow.resUpload = false;
                        //that.loadResList('', 1, that.pager.res.pageSize);
                        if (that.resArray) {
                            for (let i = 0; i < that.resArray.length; i ++ ) {
                                let __resEntry = that.resArray[i];
                                if (__resEntry.recordId == entry.recordId) {
                                    __resEntry.originalName = entry.originalName;
                                    __resEntry.currName = entry.currName;
                                    __resEntry.accessUrl = entry.accessUrl;
                                    __resEntry.resSize = entry.resSize;
                                }
                            }
                        }
                        that.$message({
                            message: '上传成功',
                            type: 'success'
                        });
                    }else{
                        this.$message.error("上传失败");
                    }
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
        					allowOrgLevel: entry.allowOrgLevel,
        					rewardDesc: entry.rewardDesc,
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
        					positionLevel:entry.positionLevel,
        					positionDesc:entry.positionDesc,
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
        	 that.$refs[type].validate((valid) => {
                 console.log('valid', valid);
                 if (valid) {
                	 switch (type) {
	             		case 'formLeader':
	             			let rowValue = that.formLeader;
	             			let scopeIndex = that.formLeader.index;
	             			that.leaderList[scopeIndex] =rowValue;
	             			that.formLeader.positonName =that.getPositionValue(that.formLeader.positon);
	             			let tmpleaderList = that.leaderList;
	             			that.leaderList = [];
	             			for(let i = 0 ; i < tmpleaderList.length ; i++){
	             				that.leaderList.push(tmpleaderList[i]);
	             			}
	             			
	             			
	             			that.dialogShow.leader =false;
	             			break;
	             		case 'formReward':
	             			let rowValue1 = that.formReward;
	             			let scopeIndex1 = that.formReward.index;
	             			that.rewardList[scopeIndex1] =rowValue1;
	             			let tmprewardList = that.rewardList;
	             			that.formReward.reward = that.getRewardNameValue(that.formReward.rewardName)
	             			that.rewardList = [];
	             			for(let i = 0 ; i < tmprewardList.length ; i++){
	             				that.rewardList.push(tmprewardList[i]);
	             			}
	             			
	             			
	             			that.dialogShow.reward =false;
	             			break;
	             		case 'formDept':
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
                     
                 } else {
                     this.$message.error('提交失败,请按要求填写表单内容');
                     return false;
                 }
               });

        	
        },
        viewDetail(scopeIndex, scopeRow){
        	let that = this;
        	let url = "/api/user/getDtl/" +scopeRow.userId;
    		axios.get(url,null)
            .then(function(response){/*成功*/
            	that.handleResponse(response);
                let data = response.data.data;
                let jsonData = JSON.parse(data);
                that.formPartInfo.gender = that.getGenderValue(scopeRow.gender);
                that.formPartInfo.nation = scopeRow.nation;
                that.formPartInfo.education = that.getEducationValue(jsonData.education);
                that.formPartInfo.bachelor = that.getBachelorValue(jsonData.bachelor);
                that.formPartInfo.orgName = that.getOrgValue(scopeRow.actOrg);
                that.formPartInfo.birthTime = jsonData.birthTime;
                that.formPartInfo.joinPartyTime = jsonData.joinPartyTime;
                that.formPartInfo.turnRightTime = jsonData.turnRightTime;
                that.formPartInfo.hometown = jsonData.hometown;
                that.formPartInfo.idCard = jsonData.idCard;
                that.formPartInfo.officeNumber = jsonData.officeNumber;
                that.formPartInfo.liveAddress = jsonData.liveAddress;
                that.formPartInfo.mail = jsonData.mail;
                that.formPartInfo.imageUrl = jsonData.imageUrl;
                that.formPartInfo.phone = jsonData.phone;
                that.formPartInfo.userName = scopeRow.userName;
                that.dialogShow.nddyxxcjdetail = !that.dialogShow.nddyxxcjdetail;
            })
            .catch(function(err){/*异常*/
            	console.log(err)
            	that.$message.error('个人信息加载失败');
            });
    		
        },
        edit(type,scopeIndex, scopeRow, isAdd) {
        	if(isAdd == null && type == 'dwjbxx'){
        		let that = this;
        		that.dwjbxxMain = true;
        		that.formdwjbxx={
        				upperOrg: scopeRow.orgId,
        				rootOrg: scopeRow.rootOrg
        		};
        		that.formdwjbxx2={};
        		that.dwjbxxDialog={
        				title:scopeRow.orgName + '下级组织添加'
        		};
        		that.leaderList = [];
        		that.rewardList = [];
        		that.deptInfoList = [];
        		
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
	                            id: entry.userId,
	                            name: entry.userName,
	                            nickname: entry.userNickname,
	                            oldPassword: '',
	                            password: '',
	                            passwordOrgi: '',
	                            typeId: entry.typeId,
	                            isHeadhunting: entry.isHeadhunting,
	                            memberId: entry.memberId,
                                recordFlag: entry.recordFlag,
                                userSex: entry.userSex,
                                userSexName: entry.userSexName,
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
	                        console.log('that.sysUsers', that.sysUsers, entry);
	                        that.formSysUser = {
	                            id: entry.userId,
	                            name: entry.userName,
	                            nickname: entry.userNickname,
	                            oldPassword: '',
	                            password: '',
	                            passwordOrgi: '',
                                recordFlag: entry.recordFlag,
                                userSex: entry.userSex,
                                userSexName: entry.userSexName,
                                orgId:entry.orgId,
                                mail:entry.mail,
                                phone:entry.phone
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
                                moduleLogo: entry.moduleLogo || '',
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
	                case 'nddyxxcj':
                    	
                    	if(isAdd){
                    		that.formnddyxxcj={
                    				leader:'0',
                    				orgId: that.nddyxxcjSelectOrg
                    		};
                    		that.imageInfo =[];
                    	}else{
                    		
                        	let url = "/api/user/getDtl/" +scopeRow.userId;
                    		axios.get(url,null)
                            .then(function(response){/*成功*/
                            	that.handleResponse(response);
                                let data = response.data.data;
                                let jsonData = JSON.parse(data);
                                jsonData.userName = scopeRow.userName;
                                jsonData.orgId = scopeRow.actOrg;
                                if(jsonData.imageUrl != null){
                                	let trueUrl = window.location.protocol+'//'+window.location.host+jsonData.imageUrl;
                                	that.imageInfo=[{name:'image.JPG',url: trueUrl}];
                                }else{
                                	that.imageInfo=[];
                                }
                                that.formnddyxxcj = jsonData;
//                                that.formnddyxxcj.orgId = scopeRow.upperOrg;
//                                that.formnddyxxcj.userId = scopeRow.userId;
//                                that.formnddyxxcj.userName = scopeRow.userName;
//                                that.formnddyxxcj.gender = scopeRow.gender;
//                                that.formnddyxxcj.nation = scopeRow.nation;
//                                that.formnddyxxcj.birthTime = jsonData.birthTime;
//                                that.formnddyxxcj.joinPartyTime = jsonData.joinPartyTime;
//                                that.formnddyxxcj.turnRightTime = jsonData.turnRightTime;
//                                that.formnddyxxcj.hometown = jsonData.hometown;
//                                that.formnddyxxcj.bachelor = jsonData.bachelor;
//                                that.formnddyxxcj.education = jsonData.education;
//                                that.formnddyxxcj.officeNumber = jsonData.officeNumber;
//                                that.formnddyxxcj.liveAddress = jsonData.liveAddress;
//                                that.formnddyxxcj.mail = jsonData.mail;
//                                if(jsonData.imageUrl != null){
//                                	that.formnddyxxcj.imageUrl = jsonData.imageUrl;
//                                	let trueUrl = window.location.protocol+'//'+window.location.host+jsonData.imageUrl;
//                                	that.imageInfo=[{name:'image.JPG',url: trueUrl}];
//                                }else{
//                                	that.formnddyxxcj.imageUrl = null;
//                                	that.imageInfo=[];
//                                }
//                                that.formnddyxxcj.phone = jsonData.phone;
                            })
                            .catch(function(err){/*异常*/
                            	this.$message.error('个人信息加载失败');
                            });
                    	}
                    	that.dialogShow.nddyxxcj = !that.dialogShow.nddyxxcj;
                    	break;
	                case 'dwjbxx':
	                	//党委基本信息新增修改
	                	
	                	if(isAdd){
	                		that.formdwjbxx={};
	                		that.dwjbxxMain = true;
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
	                        	that.handleResponse(response);
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
	                        	that.handleResponse(response);
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
	                        	that.handleResponse(response);
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
	                        	that.handleResponse(response);
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
                            		sendSms:0,
                            		sendMail:0
                            };
                            that.uploadFJList =[];
                            that.uploadFJName = [];
                            that.uploadFJUrl = [];
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
                            setTimeout(function() {
                                that.ueditors.article.setContent('', false);
                            }, 500);
                            that.showContent = 'formArticle';
                        }
                        else {
                            //that.def_menu_id = 'formArticle';
                            entry = that.articles[scopeIndex];
                            console.log('article.edit=>content', entry.content);
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
                                sendType: '',
                                receiverIds: [],
                                receiveUsers: [],
                                receiverIdArrStr: '',
                            };
                            setTimeout(function() {
                                that.ueditors.article.setContent(entry.content, false);
                            }, 500);
                            that.showContent = 'formArticle';
                        }
                        //that.dialogShow.article = !that.dialogShow.article;
                        break;
                    case 'xxjl':
                        if(isAdd) {
                            //that.def_menu_id = 'formArticle';
                            setTimeout(function() {
                                that.ueditors.article.setContent('', false);
                            }, 500);
                            that.showContent = 'formArticle';
                        }
                        else {
                            //that.def_menu_id = 'formArticle';
                            entry = that.articles[scopeIndex];
                            console.log('article.edit=>content', entry.content);
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
                                sendType: '',
                                receiverIds: [],
                                receiveUsers: [],
                                receiverIdArrStr: '',
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
                    /*case 'nddyxxcj':
                    	if(isAdd){
                    		that.formnddyxxcj={
                    		    orgId: that.nddyxxcjSelectOrg
                            };
                    	}else{
                    		
                    	}
                    	that.dialogShow.nddyxxcj = !that.dialogShow.nddyxxcj;
                    	break;*/
                    case 'res':
                        that.getResOtherTypes();
                        that.uploadFileList = [];
                        //that.$refs.uploadRes.clearFiles();
                        if(isAdd) {
                            console.log('that.formRes.typeId', that.formRes.typeId);
                            let _typeId = that.formRes.typeId + '';
                            that.formRes = {
                                    typeId: _typeId,
                                    orgId: that.formSearchRes.orgId
                            };
                            console.log('add=>that.formRes=>', that.formRes)
                        }
                        else {
                            entry = that.resArray[scopeIndex];
                            that.formRes = {
                                recordId: entry.recordId,
                                resName: entry.resName,
                                typeId: entry.typeId,
                                assId: entry.assId,
                                assTypeId: entry.assTypeId,
                                originalName: entry.originalName,
                                recordTime: entry.recordTime,
                                recordFlag: entry.recordFlag,
                                resSize: entry.resSize,
                                announcerId: entry.announcerId,
                                publishTime: entry.publishTime,
                                resAuthor: entry.resAuthor,
                                resSrc: entry.resSrc,
                                resIntro: entry.resIntro,
                                resDesc: entry.resDesc,
                                resTags: entry.resTags,
                                modifyTime: entry.modifyTime,
                                editorId: entry.editorId,
                                accessUrl: entry.accessUrl,
                                auditorId: entry.auditorId,
                                auditTime: entry.auditTime,
                                auditStatus: entry.auditStatus,
                                typeName: entry.typeName,
                                assTitle: entry.assTitle,
                                assTypeName: entry.assTypeName,
                                announcerName: entry.announcerName,
                                editorName: entry.editorName,
                                auditorName: entry.auditorName,
                                currName: entry.currName || '',
                                orgId: entry.orgId || '',
                                orgName: entry.orgName || '',
                                attaContent: entry.attaContent || '',
                                isShowFileOriginalName: true,  // 是否显示文件原始名称(当编辑时，且未选择新文件)
                            };
                        }
                        that.dialogShow.res = !that.dialogShow.res;
                        break;

	                default: break;
	            }
        	}
        },
        firstPageDyzlxz(){       	
        	let that = this;
        	if(that.firstPageZlxz == 'dygl'){
	        	that.formSearchRes.key = '';
	            that.formRes.typeId = '1e9941a0-2a6f-4c2f-b74c-970d0351469f';
	            that.formSearchRes.typeId = '1e9941a0-2a6f-4c2f-b74c-970d0351469f';
	            that.formSearchRes.assId = '';
	            that.formSearchRes.assTypeId = '';
	            that.formSearchRes.announcerId = '';
	            that.formSearchRes.currTypeName = '党员管理资料下载';
	            that.uploadData = {
	                name: '',
	                type: 'res-dygl',
	                parse: '1',
	            };
	            that.loadResList('', 1, 5);
        	}else if(that.firstPageZlxz == 'dfgl'){
        		that.formSearchRes.key = '';
                that.formRes.typeId = '01ef5219-464e-44a3-890a-557e3bbabd4e';
                that.formSearchRes.typeId = '01ef5219-464e-44a3-890a-557e3bbabd4e';
                that.formSearchRes.assId = '';
                that.formSearchRes.assTypeId = '';
                that.formSearchRes.announcerId = '';
                that.formSearchRes.currTypeName = '党费管理资料下载';
                that.uploadData = {
                    name: '',
                    type: 'res-dfgl',
                    parse: '1',
                };
                that.loadResList('', 1, 5);
        	}else if(that.firstPageZlxz == 'hjgl'){
        		that.formSearchRes.key = '';
                that.formRes.typeId = '3dea99ab-ec00-4633-b24c-7c44a5ce57b8';
                that.formSearchRes.typeId = '3dea99ab-ec00-4633-b24c-7c44a5ce57b8';
                that.formSearchRes.assId = '';
                that.formSearchRes.assTypeId = '';
                that.formSearchRes.announcerId = '';
                that.formSearchRes.currTypeName = '换届管理资料下载';
                that.uploadData = {
                    name: '',
                    type: 'res-hjgl',
                    parse: '1',
                };
                that.loadResList('', 1, 5);
        	}
        },
        dwjbxxView(type){
        	let that = this;
        	that.dwjbxxViewType=type;
        	if(type == 'baseInfo'){
        		that.dwjbxxBaseInfoType = 'danger';
        		that.dwjbxxPhoneType = '';
        		that.dwjbxxLeaderType = '';
        	}
        	if(type == 'phone'){
        		that.dwjbxxBaseInfoType = '';
        		that.dwjbxxPhoneType = 'danger';
        		that.dwjbxxLeaderType = '';
        	}
        	if(type == 'leader'){
        		that.dwjbxxBaseInfoType = '';
        		that.dwjbxxPhoneType = '';
        		that.dwjbxxLeaderType = 'danger';
        	}
        },
        handleRemove(file, fileList) {
        	let that = this;
        	that.formnddyxxcj.imageUrl=null;
            this.singleImg=true;
          },
          handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
            this.singleImg=false;
          },
        outMaxFile(){
        	  this.$message.error('无法上传多张图片');
        },
        beforeAvatarUpload(file) {
            let isImg = false ;
            if(file.type === 'image/jpeg' || file.type === 'image/png'){
            	isImg = true;
            }
            let isLt2M = file.size / 1024 / 1024 < 2;

            if (!isImg) {
              this.$message.error('上传图片只能是 JPG或PNG格式!');
            }
            if (!isLt2M) {
              this.$message.error('上传图片大小不能超过 2MB!');
            }
            
            return isImg && isLt2M;
        },
        beforeFJAvatarUpload(file) {

            let isLt2M = file.size / 1024 / 1024 < 3;


            if (!isLt2M) {
              this.$message.error('单个附件大小不能超过 3MB!');
              return false;
            }
            
            return true;
        },
        beforeNddyxxjcAvatarUpload(file) {

            let isLt2M = file.size / 1024 / 1024 < 50;


            if (!isLt2M) {
              this.$message.error('单个附件大小不能超过 50MB!');
              return false;
            }
            
            return true;
        },
        setImgUrl(response, file, fileList){
        	let that = this;
        	
        	that.formnddyxxcj.imageUrl = response.data.destName;
        },
        handleFJSuccess(response, file, fileList){
        	let that = this;
        	that.uploadFJUrl.push(response.data.filePath);
        	that.uploadFJName.push(response.data.fileName);
        	console.log(that.uploadFJUrl);
        	console.log(that.uploadFJName);
        	
        },
        handleFJRemove(file, fileList){
        	let that = this;
        	console.log(file)
        	console.log(file.response.data.filePath)
        	let urlIndex = that.uploadFJUrl.indexOf(file.response.data.filePath);
        	let nameIndex = that.uploadFJName.indexOf(file.response.data.fileName);
        	that.uploadFJUrl.splice(urlIndex,1);
        	that.uploadFJName.splice(nameIndex,1);
        },
        renderHeader (h,{column}) { // h即为cerateElement的简写，具体可看vue官方文档
        	  return h(
        	   'div',
        	   [ 
        	   h('img', {
        		   src:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png'
        	   })
        	   ]
        	  );
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
                                that.dwjbxxLoading = true;
                                axios.post("/api/org/delOrg",params)
                                    .then(function(response){
                                    	that.handleResponse(response);
                                        if(parseInt(response.data.code) === 200){
                                        	that.loadDwjbxx();
                                            that.$message({
                                                message: '删除成功',
                                                type: 'success'
                                            });
                                        }else{
                                        	that.$message.error("删除失败");
                                        }
                                        that.dwjbxxLoading = false;
                                    }).catch(function(err){
                            			that.dwjbxxLoading = false;
                            			that.$message.error("出现异常，请联系管理员");
                                    });
                                break;
                            case 'nddyxxcj':
                            	let userId = row.userId;
                            	let params2 = new URLSearchParams();
                            	params2.append("userId",userId);
                            	that.nddyxxcjLoading = true;
                            	
                                axios.post("/api/org/delOrgUser",params2)
                                    .then(function(response){
                                    	that.handleResponse(response);
                                        if(parseInt(response.data.code) === 200){
                                        	that.loadNddyxxcj(that.pager.orgUser.currentPage,that.pager.orgUser.pageSize);
                                            that.$message({
                                                message: '删除成功',
                                                type: 'success'
                                            });
                                        }else if(parseInt(response.data.code) === 2000){
                                        	that.$message.error(response.data.msg);
                                        }
                                        else{
                                        	that.$message.error("删除失败");
                                        }
                                        that.nddyxxcjLoading = false;
                                    }).catch(function(err){
                                    	that.nddyxxcjLoading = false;
                                    	that.$message.error("出现异常，请联系管理员");
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
                                let sysUserEntry = that.sysUsers[idx];
                                let sysUserParams = new URLSearchParams();
                                sysUserParams.append("userId", sysUserEntry.userId);
                                axios.post("/api/user/deleteByUserId", sysUserParams)
                                    .then(function(response){
                                    	that.handleResponse(response);
                                        if(parseInt(response.data.code) === 200){
                                            that.sysUsers.splice(idx, 1);
                                            that.pager.sysUser.currentPage = 1;
                                            that.loadSysUsers('', 1, that.pager.sysUser.pageSize);
                                            that.$message({
                                                message: '系统用户信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;
                            case 'memberUser':
                                let memberUserEntry = that.sysUsers[idx];
                                let memberUserParams = new URLSearchParams();
                                memberUserParams.append("userId", memberUserEntry.userId);
                                axios.post("/api/user/deleteByUserId", memberUserParams)
                                    .then(function(response){
                                    	that.handleResponse(response);
                                        if(parseInt(response.data.code) === 200){
                                            that.memberUsers.splice(idx, );
                                            that.pager.user.currentPage = 1;
                                            that.loadMemberUsers('', '3', 1, that.pager.user.pageSize);
                                            that.$message({
                                                message: '用户信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                    }).catch(function(err){
                                    console.warn(err);
                                });
                                break;
                            case 'authModule':
                                let authModuleEntry = that.authModules[idx];
                                let moduleParams = new URLSearchParams();
                                moduleParams.append("id", authModuleEntry.moduleId);
                                moduleParams.append("flag", '0');
                                axios.post("/api/user/updateModuleFlagById",moduleParams)
                                    .then(function(response){
                                    	that.handleResponse(response);
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
                                    	that.handleResponse(response);
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
                                    	that.handleResponse(response);
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
                                    	that.handleResponse(response);
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
                                    	that.handleResponse(response);
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
                                    	that.handleResponse(response);
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
                                    	that.handleResponse(response);
                                        if(parseInt(response.data.code) === 200){
                                            that.articles.splice(idx,1);
                                            that.pager.article.currentPage = 1;
                                            that.loadArticles(that.formSearchArticle.key, 1, that.pager.article.pageSize);
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
                                    	that.handleResponse(response);
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
                            case 'res':
                                entry = that.resArray[idx];
                                params = new URLSearchParams();
                                params.append('resId', entry.recordId);
                                params.append('flag','0');
                                axios.post("/api/res/deleteById",params)
                                    .then(function(response){
                                    	that.handleResponse(response);
                                        if(parseInt(response.data.code) === 200){
                                            that.resArray.splice(idx,1);
                                            that.pager.res.currentPage = 1;
                                            that.loadResList('', 1, that.pager.res.pageSize);
                                            that.$message({
                                                message: '资源信息删除成功!',
                                                type: 'success'
                                            });
                                        }
                                        else {
                                            that.$message({
                                                message: '资源信息删除失败!',
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
                	that.handleResponse(response);
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
        deletefzdyByOrg(){
        	let that = this;
        	that.$confirm('重置发展党员进度表将清空所有进度，是否重置', '警告', {
                confirmButtonText: '是',
                cancelButtonText: '否',
                type: 'warning',
                callback: action => {
                    if(action === "cancel"){
                        that.$message({
                            type: 'info',
                            message: "取消重置"
                        });
                    }else{ 
                        let params = new URLSearchParams();
                        params.append("orgId",that.fzdyCurrentOrg);
                        that.dwjbxxLoading = true;
                        axios.post("/api/preParty/deletefzdyByOrg",params)
                            .then(function(response){
                            	that.handleResponse(response);
                                if(parseInt(response.data.code) === 200){
                                	that.loadfzdy(that.fzdyCurrentOrg);
                                    that.$message({
                                        message: '重置成功',
                                        type: 'success'
                                    });
                                }else{
                                	that.$message.error("重置失败");
                                }
                                that.dwjbxxLoading = false;
                            }).catch(function(err){
                    			that.dwjbxxLoading = false;
                    			that.$message.error("出现异常，请联系管理员");
                            });
                                
                        }
                    }
            });
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
                	that.handleResponse(response);
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
                            roleModuleParams.append('roleId', that.checkBoxRelationId.roleModule);
                            roleModuleParams.append('moduleIds',that.checkBoxOptions.roleModule);
                            axios.post("/api/user/saveRoleModule", roleModuleParams)
                                .then(function(response){
                                	that.handleResponse(response);
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
                            userRoleParams.append('userId', that.checkBoxRelationId.userRole);
                            userRoleParams.append('roleIds', that.checkBoxOptions.userRole);
                            axios.post("/api/user/saveUserRole", userRoleParams)
                                .then(function(response){
                                	that.handleResponse(response);
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
                            sysUserRoleParams.append('userId', that.checkBoxRelationId.sysUserRole);
                            sysUserRoleParams.append('roleIds', that.checkBoxOptions.sysUserRole);
                            axios.post("/api/user/saveUserRole", sysUserRoleParams)
                                .then(function(response){
                                	that.handleResponse(response);
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
         * 获得所有系统用户信息
         */
        getAllSysUsers() {
            let that = this;
            that.allSysUsers = [];
            axios.get("/api/user/allSysUsers")
                .then(function(response){
                	that.handleResponse(response);
                	let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.allSysUsers = data.data;
                    }
                })
                .catch(function(err){/!*异常*!/
                    console.log(err);
                });
        },

        /**
         * 获得所有系统用户信息(Map结构)
         */
        getAllSysUsersMap() {
            let that = this;
            that.allSysUsersMap = [];
            axios.get("/api/user/allSysUsersMap")
                .then(function(response){/!*成功*!/
                	that.handleResponse(response);
                	let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.allSysUsersMap = data.data;
                    }
                })
                .catch(function(err){/!*异常*!/
                    console.log(err);
                });
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
                	that.handleResponse(response);
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
         * 根据类型获得字典信息
         * @param _type 若为空，则查询所有
         */
        getDictByType(_type) {
            let that = this;
            that.typeDicts = [];
            axios.get("/api/dict/getByType", {params:{
                    type: _type
                }})
                .then(function(response){
                	that.handleResponse(response);
                	let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.typeDicts = data.data;
                    }
                })
                .catch(function(err){/!*异常*!/
                    console.log(err);
                });
        },

        /**
         * 获取资源其他分类
         */
        getResOtherTypes() {
            let that = this;
            that.resOtherTypes = [];
            axios.get("/api/dict/getByType", {params:{
                    type: 'resOtherType'
                }})
                .then(function(response){
                	that.handleResponse(response);
                    let data = response.data;
                    that.resOtherTypes = data.data;
                })
                .catch(function(err){/!*异常*!/
                    console.log(err);
                });
        },

        /**
         * 获得年月信息
         */
        getYearMonths() {
            let that = this;
            that.yearMonths = [];
            axios.get("/api/dict/getYearMonths")
                .then(function(response){
                	that.handleResponse(response);
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.yearMonths = data.data;
                    }
                })
                .catch(function(err){/*异常*/
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
                .then(function(response){
                	that.handleResponse(response);
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
                .then(function(response){
                	that.handleResponse(response);
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.articleCategories = data.data;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        setDwjbxxAuth(){
        	let that = this;
        	let roles = that.currentUser.authorities;
        	for (let i = 0; i < roles.length; i ++) {
        		if(roles[i].roleId == '2d3b447b-a82f-4295-9fe0-4f64f500b48b'){
        			that.isSuperAdmin = true;
        			break;
        		}
        	}
        	
        },

        /**
         * 首页加载通知消息
         */
        loadCurrUserNewsOfFirstPage() {
            let that = this;
            that.scrollBoxContent = '';
            axios.get("/api/news/getCurrUserNews", {params:{
                    pageNum: 1,
                    pageSize: 3
                }})
                .then(function(response){
                    that.handleResponse(response);
                    let data = response.data;
                    //console.log('getCurrUserReceivedNewestNews.data ', data.data.list)
                    if(parseInt(data.code) === 200) {
                        let _newsArr = data.data.list || [];
                        that.currUserReceiverNewsRecordsfirstPage = _newsArr;
                        let _content = '';
                        for (let i = 0; i < _newsArr.length; i ++) {
                            let _news = _newsArr[i];
                            if (_content == '') {
                                _content = _news.content;
                            }
                            else {
                                _content += '      ' + _news.content;
                            }
                        }
                        that.scrollBoxContent = _content;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },

        /**
         * 获得(当前)用户获得最新的消息通告
         */
        getCurrUserReceivedNewestNews() {
            let that = this;
            that.loadCurrUserNewsOfFirstPage();
            let timer = setInterval(function () {
                that.loadCurrUserNewsOfFirstPage();
            },300000);

            // that.scrollBoxContent = '';
            // axios.get("/api/news/getCurrUserReceivedNewestNews")
            //     .then(function(response){/*成功*/
            //         let data = response.data;
            //         if(parseInt(data.code) === 200) {
            //             that.scrollBoxContent = data.data.content || '';
            //         }
            //     })
            //     .catch(function(err){/*异常*/
            //         console.log(err);
            //     });
        },
        getMenuData(list, dataArr) {
        	//TODO
            dataArr.map((pNode, i) => {
              let childObj = []
              list.map((cNode, j) => {
                if (pNode.moduleId === cNode.parentId) {
                      childObj.push(cNode)
                }
              })
              pNode.subs = childObj
              if(pNode.subs.length ==0){
            	  pNode.subs = null;
              }
              if (childObj.length > 0) {
                this.getMenuData(list, childObj)
              }
            })
            return dataArr
          },
          
          /**
           * 获得当前用户信息
           */
          getCurrentUserInfo() {
              let that = this;
              that.currentUser = {};
              that.userOwnedModuleMaps = {};
              that.userOwnedModules = [];
              that.userOwnedMenus = [];

              axios.get("/api/auth/getCurrentUser")
                  .then(function(response){/*成功*/
                  	that.handleResponse(response);
                  	let data = response.data;
                      that.currentUser = data.data;
                      that.currentUserStr = JSON.stringify({
	                      id: data.data.userId,
	                      name: data.data.username,
	                      nickname: data.data.userNickname,
	                      isAdmin: that.isSuperAdmin
	                    });
                      console.log('currentUser ==== ',that.currentUser,that.currentUserStr);
                     let _src = that.chatConfig.web.url || '';
                     //let _src = 'http://www.awycjcdj.com:8090/a.html';
			 that.chatSrc = `${_src}?data=` + that.currentUserStr;
                      console.log('oa ===== ',that.chatSrc);
                      that.setDwjbxxAuth();
                      let __modules = data.data['modules'] || [];
                      //let parentArr = __modules.filter(l => (l.parentId === null || l.parentId === ''));
                      //that.userOwnedMenus = that.getMenuData(__modules,parentArr);
                      that.userOwnedModules = __modules;
                      // 初始化当前用户所拥有(的所有角色)的模块<Map格式>
                      for (let i = 0; i < __modules.length; i ++) {
                          let __module = __modules[i];
                          let _moduleCode = '';
                          if (__module && __module.moduleCode) {
                              _moduleCode = __module.moduleCode || '';
                          }
                          that.userOwnedModuleMaps[_moduleCode] = __module;
                      }
                      // console.log('that.userOwnedModuleMaps.__modules', __modules, that.userOwnedModuleMaps)

                      let moduleLen = __modules.length;
                      for (let i = 0; i < moduleLen; i ++) {
                          let __module = __modules[i];
                          let __moduleId = __module.moduleId;
                          if (__module.isMenu == 1) {
                              let __moduleUrl = __module.moduleUrl || '';
                              if (__module.parentId == '' || __module.parentId == null) {
                                  let __subMenus = [];
                                  if (__moduleUrl === '') { // 有子菜单
                                      for (let j = 0; j < moduleLen; j++) {
                                          let __module2 = __modules[j];
                                          if (__module2.isMenu == 1 && __module2.parentId == __moduleId) {
                                              __subMenus.push(__module2);
                                          }
                                      }
                                      if (__subMenus.length > 0) {
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
                                              moduleLogo: __module.moduleLogo,
                                              isMenu: __module.isMenu,
                                              subs: __subMenus,
                                          });
                                      }
                                  }
                                  else {  // 没有子菜单
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
                                          moduleLogo: __module.moduleLogo,
                                          isMenu: __module.isMenu,
                                          subs: __subMenus,
                                      });
                                  }


                              }
                          }
                      }
                      console.log(that.userOwnedMenus)
                      /*if(that.isSuperAdmin){
                  		that.getAdminUpperOrg();
                  	}else{
                  		that.getUserUpperOrgList();
                  	}*/
                      that.getUserRootOrgList();
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
        handleOrgUserSizeChange: function(val) {
            this.pager.orgUser.pageSize = val;
            this.loadNddyxxcj( this.pager.orgUser.currentPage, this.pager.orgUser.pageSize);
        },
        //页码变更
        handleOrgUserCurrentChange: function(val) {
            this.pager.orgUser.currentPage = val;
            this.loadNddyxxcj(this.pager.orgUser.currentPage, this.pager.orgUser.pageSize);
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
        loadMemberUsers(criteria, typeId, pageNum, pageSize) {
            let that = this;
            axios.get("/api/user/search",{params:{
                    key: criteria,
                    userType: typeId,
                    pageNum: pageNum,
                    pageSize:pageSize
                }})
                .then(function(response){
                	that.handleResponse(response);
                	if(parseInt(response.status) == 200 ) {
                        that.memberUsers = response.data.data.list ? response.data.data.list : []
                        that.pager.user.totalCount = response.data.data.total || 0;
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
            that.sysUserLoading = true;
            axios.get("/api/user/search",{params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize:pageSize,
                    userType: 1,
                    isSuperAdmin: that.isSuperAdmin
                }})
                .then(function(response){/*成功*/
                	that.handleResponse(response);
                	if(parseInt(response.status) == 200 ) {
                    	let result = response.data.data.list;
                    	for(let i = 0; i < result.length;i++){
                    		if(result[i].orgId != null && result[i].orgId != ""){
                    			result[i].orgId = result[i].orgId.split(',');
                    			
                    		}
                    	}
                        that.sysUsers = result;
                        that.pager.sysUser.totalCount = response.data.data.total;
                    }
                    that.sysUserLoading = false;
                })
                .catch(function(err){/*异常*/
                	that.sysUserLoading = false;
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
        gzzdDownLoad(fileName){
        	window.open('/api/file/dlRes/res-dfgl/dzzgl.pdf/dzzgl.pdf'
            );
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
        	that.dwjbxxLoading = true;
        	that.fzdyLoading = true;
        	axios.get("/api/org/getOrgList",{params:{
        		isSuperAdmin:that.isSuperAdmin
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.data.code) == 200 ){
        			let parentArr = response.data.result.filter(l => l.upperOrg === null);
        			that.dwjbxxTreeLevel.level = 0;
        			that.dwjbxxTreeData = that.getTreeData(response.data.result, parentArr);
        			if(that.isSuperAdmin){
        				that.dwjbxxTreeData.push(that.adminOrgAdd);
        			}
        			that.dwjbxxLoading = false;
        		}else{
        			that.$message.error('数据加载失败');
        			that.dwjbxxLoading = false;
        		}
            	that.fzdyLoading = false;
        	}).catch(function(err){/*异常*/
        		that.$message.error('请求失败');
        		that.dwjbxxLoading = false;
        		that.fzdyLoading = false;
                });
        },
        loadfzdy(orgid){
        	let that = this;
        	that.fzdyLoading = true;
            axios.get("/api/preParty/getPrePartyList",{params:{
                    orgId: orgid
                }})
                .then(function(response){/*成功*/
                    that.handleResponse(response);
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        that.fzdyPartyMembers = response.data.result
                        console.log(that.fzdyPartyMembers)
                    }
                    that.fzdyLoading = false;
                })
                .catch(function(err){/*异常*/
                	that.fzdyLoading = false;
                });
        },
        getDwjbxxTreeData(list, dataArr) {
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
        nddyxxcjHandleNodeClick(data){
        	let that = this;
        	that.dwjbxxMain = true;
        	that.fullscreenLoading = true;
        	let searchName = '';
        	that.nddyxxcjSelectOrg = data.orgId;
        	if (data.orgType === 'orgType3') {
                that.dwjbxxMainAdd = true;
            }
        	else {
                that.dwjbxxMainAdd = false;
            }
        	console.log(data.orgId)
        	if(that.nddyxxSearchCondition == null || that.nddyxxSearchCondition == ''){
        		searchName = null;
        	}else{
        		searchName = that.nddyxxSearchCondition;
        	}
        	axios.get("/api/org/getOrgUserListByOrg",{params:{
                userName: searchName,
               /* year: that.dyxxyear.year,*/
                orgId: data.orgId
            }})
            .then(function(response){/*成功*/
            	that.handleResponse(response);
            	
            	if(parseInt(response.data.code) == 200 ){
            		
        			that.nddyxxcjTableData = response.data.result.list;
        			console.log(response.data.result)
        			that.fullscreenLoading = false;
        			that.pager.orgUser.totalCount = response.data.result.total;
        		}else{
        			that.$message.error('数据加载失败');
        			that.fullscreenLoading = false;
        		}
                that.fullscreenLoading = false;
            })
            .catch(function(err){/*异常*/
            	that.fullscreenLoading = false;
            });
        	
        	
        },

        /**
         * 获得上级节点列表
         * @param node 当前节点
         * @param parentNodeList 上级节点列表
         */
        getParentOrgs(node, parentNodeList = []) {
            let that = this;
            if (!node || !node.data || !node.data.upperOrg || node.data.upperOrg == null || node.data.upperOrg == '') {
                return parentNodeList;
            }

            let upperOrg = node.data.upperOrg || '';
            if (upperOrg !== '') {
                parentNodeList.push(node.parent);
                return that.getParentOrgs(node.parent, parentNodeList)
            }
            else {
                return parentNodeList;
            }
        },

        /**
         * 当组织树节点点击事件处理
         * @param node 节点
         */
        dzzTreeNodeClickHandle(node) {
            let that = this;
            let data = node.data;
            if (that.showContent == 'fzdy') {   // 发展党员特殊处理
                console.log('dzzTreeNodeClickHandle-node', node)
                that.fzdyListShow = false;
                that.fzdyPrePartyMemberAdd = false;
                that.fzdyPrePartyMemberSetps = false;
                let upperOrgs = that.getParentOrgs(node).reverse();
                let _fzdyBreadcrumbs = [];
                let _upperOrgsLen = upperOrgs.length;
                for (let i = 0; i < _upperOrgsLen; i ++) {
                    let _upperOrg = upperOrgs[i];
                    _fzdyBreadcrumbs.push({
                        id: i + 1,
                        title: _upperOrg.label || '',
                    })
                }
                _fzdyBreadcrumbs.push({
                    id: _upperOrgsLen + 1,
                    title: node.label || ''
                })
                that.fzdyBreadcrumbs = _fzdyBreadcrumbs;
                // if (node.childNodes.length === 0 && node.data.orgType === 'orgType3') {
                if (node.data.orgType === 'orgType2' || node.data.orgType === 'orgType3') {
                    that.fzdyPrePartyMemberSetps = true;
                    if (node.data.orgType === 'orgType3') {
                        that.fzdyPrePartyMemberAdd = true;
                    }
                    that.fzdyCurrentOrg = node.data.orgId;
                    that.loadfzdy(node.data.orgId);
                }
                else if(node.data.orgType === 'orgType1') {
                    that.fzdyListShow = true;
                    //加载发展党员列表
                    // that.getResOtherTypes();
                    that.formSearchRes.key = '';
                    that.formRes.typeId = 'ed535138-6ec2-468c-8083-d967a24c2f33';
                    that.formRes.typeName = '发展党员';
                    that.formSearchRes.typeId = 'ed535138-6ec2-468c-8083-d967a24c2f33';
                    that.formSearchRes.assId = '';
                    that.formSearchRes.assTypeId = '';
                    that.formSearchRes.announcerId = '';
                    that.formSearchRes.currTypeName = '发展党员';
                    that.formSearchRes.orgId = node.data.orgId;
                    // that.formSearchRes.yearMonth = that.getCurrYearMonth();
                    that.uploadData = {
                        name: '',
                        type: 'res2-fzdy',
                        parse: '1',
                    };
                    that.loadResList('', 1, that.pager.res.pageSize);
                }
            }else{

	            that.dwjbxxMain = true;
	            that.fullscreenLoading = true;
	            axios.get("/api/org/getOrgDetailById",{params:{
	                    orgId: data.orgId
	                }})
	                .then(function(response){/*成功*/
	                    that.handleResponse(response);
	                    let data = response.data;
	                    if(parseInt(data.code) === 200) {
	                        that.formdwjbxx = response.data.result;
	                        that.formdwjbxx2 = response.data.result;
	
	                        that.dwjbxxDialog.title = that.formdwjbxx.orgName;
	
	                    }
	                    that.fullscreenLoading = false;
	                })
	                .catch(function(err){/*异常*/
	                    console.log(err);
	                    that.fullscreenLoading = false;
	                });
	            //领导班子
	            axios.get("/api/org/getOrgLeaderList",{params:{
	                    orgId: data.orgId
	                }})
	                .then(function(response){/*成功*/
	                    that.handleResponse(response);
	                    let data = response.data;
	                    if(parseInt(data.code) === 200) {
	                        that.leaderList = response.data.result
	                    }
	                })
	                .catch(function(err){/*异常*/
	                });
            }
        },

        dwjbxxHandleNodeClick(data){
        	let that = this;
        	that.dwjbxxMain = true;
        	that.fullscreenLoading = true;
    		axios.get("/api/org/getOrgDetailById",{params:{
    			orgId: data.orgId
            }})
            .then(function(response){/*成功*/
            	that.handleResponse(response);
                let data = response.data;
                if(parseInt(data.code) === 200) {
                	that.formdwjbxx = response.data.result;
                	that.formdwjbxx2 = response.data.result;
                	
                	that.dwjbxxDialog.title = that.formdwjbxx.orgName;
                	
                }
                that.fullscreenLoading = false;
            })
            .catch(function(err){/*异常*/
                console.log(err);
                that.fullscreenLoading = false;
            });
    		//领导班子
    		axios.get("/api/org/getOrgLeaderList",{params:{
    			orgId: data.orgId
            }})
            .then(function(response){/*成功*/
            	that.handleResponse(response);
                let data = response.data;
                if(parseInt(data.code) === 200) {
                	that.leaderList = response.data.result
                }
            })
            .catch(function(err){/*异常*/
            });
            console.log('dwjbxxHandleNodeClick', data, that.dwjbxxTreeData, that.getParentOrgs(data));
        },
        loadNddyxxcj(pagenum,pagesize){
        	let that = this;
        	that.fullscreenLoading = true;
        	let searchName = '';
        	if(that.nddyxxSearchCondition == null || that.nddyxxSearchCondition == ''){
        		searchName = null;
        	}else{
        		searchName = that.nddyxxSearchCondition;
        	}
        	axios.get("/api/org/getOrgUserListByOrg",{params:{
                userName: searchName,
            //    year: that.dyxxyear.year,
                orgId: that.nddyxxcjSelectOrg,
                pageNum: pagenum,
                pageSize: pagesize
            }})
            .then(function(response){/*成功*/
            	that.handleResponse(response);
            	
            	if(parseInt(response.data.code) == 200 ){
            		that.dwjbxxMain = true;
        			that.nddyxxcjTableData = response.data.result.list;
        			console.log(that.nddyxxcjTableData)
        			that.fullscreenLoading = false;
        			that.pager.orgUser.totalCount = response.data.result.total;
        		}else{
        			that.$message.error('数据加载失败');
        			that.fullscreenLoading = false;
        		}
                that.fullscreenLoading = false;
            })
            .catch(function(err){/*异常*/
            	that.fullscreenLoading = false;
            });
        },
        getUpperOrg(){
        	let that = this;
        	let treeTable =[];
        	axios.get("/api/org/getUpperOrgList",null).then(function(response){
        		that.handleResponse(response);
        		if(parseInt(response.status) == 200 ){
        			for(let i =0 ; i <response.data.result.length;i++){
        				response.data.result[i].value=response.data.result[i].orgId;
        				response.data.result[i].label=response.data.result[i].orgName;
        			}
        			let parentArr = response.data.result.filter(l => l.upperOrg === null);
        			that.upperOrg = that.getTreeData(response.data.result, parentArr);
        			that.orignUpperOrg = response.data.result;
        		}
        	});
        },
        getAdminUpperOrg(){
        	let that = this;
        	let treeTable =[];
        	axios.get("/api/org/getUpperOrgList",null).then(function(response){
        		that.handleResponse(response);
        		if(parseInt(response.status) == 200 ){
        			for(let i =0 ; i <response.data.result.length;i++){
        				response.data.result[i].value=response.data.result[i].orgId;
        				response.data.result[i].label=response.data.result[i].orgName;
        			}
        			let parentArr = response.data.result.filter(l => l.upperOrg === null);
        			that.userUpperOrg = that.getTreeData(response.data.result, parentArr);
        			
        			that.orignUpperOrg = response.data.result;
        		}
        	});
        },
        getUserUpperOrgList(){
        	let that = this;
        	let treeTable =[];
        	that.nddyxxcjLoading = true;
        	axios.get("/api/org/getUserUpperOrgList",null).then(function(response){
        		that.handleResponse(response);
        		that.nddyxxcjLoading = false;
        		if(parseInt(response.status) == 200 ){
        			for(let i =0 ; i <response.data.result.length;i++){
        				response.data.result[i].value=response.data.result[i].orgId;
        				response.data.result[i].label=response.data.result[i].orgName;
        			}
        			let parentArr = response.data.result.filter(l => l.upperOrg === null);
        			that.userUpperOrg = that.getTreeData(response.data.result, parentArr);
        		}
        	})
        	.catch(function(err){/*异常*/
        		that.nddyxxcjLoading = false;
                });
        },
        getUserRootOrgList(){
        	let that = this;
        	let treeTable =[];
        	axios.get("/api/org/getRootOrgList",null).then(function(response){
        		that.handleResponse(response);
        		if(parseInt(response.status) == 200 ){
        			for(let i =0 ; i <response.data.result.length;i++){
        				response.data.result[i].value=response.data.result[i].orgId;
        				response.data.result[i].label=response.data.result[i].orgName;
        			}
        			let parentArr = response.data.result.filter(l => l.upperOrg === null);
        			that.userRootOrg = that.getTreeData(response.data.result, parentArr);
        		}
        	});
        },
        getNddyxxOptions(){
        	let that = this;
        	/*axios.get("/api/dict/search",{params:{
        		dictType:'nation'
            }}).then(function(response){
        		if(parseInt(response.status) == 200 ){
        			that.nation = response.data.data.list;
        		}
        	});*/
        	axios.get("/api/dict/search",{params:{
        		dictType:'bachelor'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			that.bachelor = response.data.data.list;
        		}
        	});
        	axios.get("/api/dict/search",{params:{
        		dictType:'education'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			that.education = response.data.data.list;
        		}
        	});
        },
        getTreeDict(){
        	let that = this;
        	axios.get("/api/treeDict/getTreeDictByType",{params:{
        		treeType:'2'
            }}).then(function(response){
            	that.handleResponse(response);
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
        getAreaTreeDict(){
        	let that = this;
        	axios.get("/api/treeDict/getTreeDictByType",{params:{
        		treeType:'4'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			for(let i =0 ; i <response.data.result.length;i++){
        				response.data.result[i].value=response.data.result[i].nodeId;
        				response.data.result[i].label=response.data.result[i].nodeName;
        			}
        			let parentArr = response.data.result.filter(l => l.upperNode === null);
        			that.belongArea = that.getTreeDictData(response.data.result, parentArr);
        		}
        	});
        },
        getRewardTreeDict(){
        	let that = this;
        	axios.get("/api/treeDict/getTreeDictByType",{params:{
        		treeType:'3'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			for(let i =0 ; i <response.data.result.length;i++){
        				response.data.result[i].value=response.data.result[i].nodeId;
        				response.data.result[i].label=response.data.result[i].nodeName;
        			}
        			that.orignRewardName = response.data.result;
        			let parentArr = response.data.result.filter(l => l.upperNode === null);
        			that.rewardName = that.getTreeDictData(response.data.result, parentArr);
        		}
        	});
        },
        getOptionDict(){
        	let that = this;
        	axios.get("/api/dict/search",{params:{
        		dictType:'leadTime'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			that.leadTime = response.data.data.list;
        		}
        	});
        	axios.get("/api/dict/search",{params:{
        		dictType:'orgType'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			that.orgType = response.data.data.list;
        		}
        	});
        	axios.get("/api/dict/search",{params:{
        		dictType:'isDelPartPersonAuth'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			that.isDelPartPersonAuth = response.data.data.list;
        		}
        	});
        	axios.get("/api/dict/search",{params:{
        		dictType:'elecType'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			that.elctType = response.data.data.list;
        		}
        	});
        	axios.get("/api/dict/search",{params:{
        		dictType:'positionType'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			that.positon = response.data.data.list;
        		}
        	});
        	/*axios.get("/api/dict/search",{params:{
        		dictType:'positionLevel'
            }}).then(function(response){
        		if(parseInt(response.status) == 200 ){
        			that.positionLevel = response.data.data.list;
        		}
        	});*/
        	
        	axios.get("/api/dict/search",{params:{
        		dictType:'allowOrgLevel'
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.status) == 200 ){
        			that.allowOrgLevel = response.data.data.list;
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
          getNddyxxTreeData(list, dataArr,that){
        	  
        	  dataArr.map((pNode, i) => {
        		 if(that.isSuperAdmin){
        			 pNode.isOperate = 1 ;
        		 }else if(that.currentUserOrgId.indexOf(pNode.actOrg) >= 0){
        			 pNode.isOperate = 1 ;
        		 }
              	/*if(pNode.userId == that.currentUser.userId){
              		pNode.isOperate = 1 ;
              	}*/
                let childObj = []
                list.map((cNode, j) => {
                  if (pNode.orgId === cNode.upperOrg) {

                  	if(pNode.isOperate == 1){
                  		cNode.isOperate = 1;
                  	}
                  	if(that.isSuperAdmin){
                  		cNode.isOperate = 1 ;
                  	}else if(that.currentUserOrgId.indexOf(cNode.actOrg) >= 0){
                  		cNode.isOperate = 1 ;
                  	}
                  	/*if(cNode.userId == that.currentUser.userId){
                  		cNode.isOperate = 1;
                  	}*/
                    childObj.push(cNode)
                  }
                })
                pNode.children = childObj
                if(pNode.children.length ==0){
              	  pNode.children = null;
                }
                if (childObj.length > 0) {
                  this.getNddyxxTreeData(list, childObj,that)
                }
              })
              return dataArr
          },
          resetPwd(row){
          	let that = this;
          	axios.get("/api/user/resetPwd",{params:{
                  userId:row.userId
              }})
              .then(function(response){/*成功*/
            	  that.handleResponse(response);
            	  if(parseInt(response.data.code) === 200){
      				
                      that.$message({
                          message: '重置成功',
                          type: 'success'
                      });
                  }else {
                  	that.$message.error("重置失败");
                  }
              })
              .catch(function(err){/*异常*/
              	that.$message.error("重置失败");
              });
          },
        /**
         * 处理没有children结构的数据
         */
        getTreeData(list, dataArr,committeeFlag) {
        	  console.log('committeeFlag ====',committeeFlag)
            dataArr.map((pNode, i) => {
            	if(pNode.level == null){
            		pNode.level = 0 ;
            	}
              let childObj = []
              list.map((cNode, j) => {
            	  if (committeeFlag){
            		  if (pNode.orgId === cNode.upperOrg && cNode.orgType == `orgType1`) {
                    	cNode.level = pNode.level+1;
                    	childObj.push(cNode)
	                   }
            		  
            	  } else {
            		  if (pNode.orgId === cNode.upperOrg) {
                      	cNode.level = pNode.level+1;
                        childObj.push(cNode)
                      }
            	  }
                
              })
              pNode.children = childObj
              if(pNode.children.length ==0){
            	  pNode.children = null;
              }
              if (childObj.length > 0) {
                this.getTreeData(list, childObj,committeeFlag)
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
                	that.handleResponse(response);
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
            that.newsLoading = true;
            axios.get("/api/news/getCurrUserNews",{params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize: pageSize,
                    isViewed: _isViewed,
                }})
                .then(function(response){/*成功*/
                	that.handleResponse(response);
                	that.newsLoading = false;
                	if(parseInt(response.status) == 200 ) {
                        that.newsArray = response.data.data.list;
                        that.pager.news.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                	that.newsLoading = false;
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
            //console.log('newsReceiveUserIds => ', that.newsReceiveUserIds);
            let _len = that.newsReceiveUserIds.length;
            if (_len > 0) {
                // 向表单中添加记录
                for (let i = 0; i < _len; i ++) {
                    let _newsReceiveUserId = that.newsReceiveUserIds[i];
                    let _user = that.allSysUsersMap[_newsReceiveUserId];
                    if (_user) {
                        that.formNews.receiveUserIds.push(_newsReceiveUserId);
                        that.formNews.receiveUsers.push(_user);
                    }
                }
                that.formNews.receiverId = that.formNews.receiveUserIds.length > 0 ? that.formNews.receiveUserIds.join(",") : '';
                // console.log('(handleNewsAddReceivers)that.formNews.receiveUserIds', that.formNews.receiveUserIds);
                // console.log('(handleNewsAddReceivers)that.formNews.receiveUsers', that.formNews.receiveUsers);
                // console.log('(handleNewsAddReceivers)that.formNews.receiverId', that.formNews.receiverId);
                that.$forceUpdate();
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
                that.formNews.receiverId = _ids.length > 0 ? _ids.join(",") : '';
                //console.log('(handleNewsRemoveReceiver)that.formNews.receiverId', that.formNews.receiverId);
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
                if (parseInt(_record.status) != 1) {
                    let params = new URLSearchParams();
                    params.append('recordId', _recordId);
                    axios.post("/api/news/viewNews", params)
                        .then(function(response){
                        	that.handleResponse(response);
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
         * 查看简报
         * @param recordId 当前记录唯一标识
         */
        handleBriefView: function(_recordId) {
            let that = this;
            _recordId = _recordId || '';
            that.currBrief = {};
            if ('' != _recordId) {
                axios.get("/api/article/search", {params:{
                        recordId: _recordId,
                    }})
                    .then(function(response){
                    	that.handleResponse(response);
                    	if(parseInt(response.data.code) === 200){
                            //console.log('handleBriefView => response.data.data', response.data.data)
                            that.currBrief = response.data.data;
                            that.dialogShow.viewBrief = !that.dialogShow.viewBrief;
                        }
                    }).catch(function(err){
                    console.warn(err);
                });
            }

        },

        /**
         * 查看首页消息
         * @param recordId 当前记录唯一标识
         */
        handleNoticeView: function(_recordId) {
            let that = this;
            _recordId = _recordId || '';
            that.currNotice = {};
            if ('' != _recordId && that.currUserReceiverNewsRecordsfirstPage) {
                for (let i = 0; i < that.currUserReceiverNewsRecordsfirstPage.length; i ++) {
                    let __notice = that.currUserReceiverNewsRecordsfirstPage[i];
                    if ((__notice.recordId || '') === _recordId) {
                        that.currNotice = __notice;
                        that.dialogShow.viewNotice = !that.dialogShow.viewNotice;
                        let params = new URLSearchParams();
                        params.append('recordId', _recordId);
                        axios.post("/api/news/viewNews", params)
                            .then(function(response){
                            	that.handleResponse(response);
                            	if(parseInt(response.data.code) === 200){
                                    //that.searchForm('formSearchNews');
                                    console.log('查看成功。。。', _recordId);
                                    that.loadCurrUserNewsOfFirstPage();
                                }
                            }).catch(function(err){
                            console.warn(err);
                        });
                        break;
                    }
                }
            }

        },


        /**
         * (简报)新增接收人
         */
        handleBriefAddReceivers: function(e) {
            let that = this;
            that.formArticle.receiverIds = that.formArticle.receiverIds || [];
            that.formArticle.receiveUsers = that.formArticle.receiveUsers || [];
            //console.log('newsReceiveUserIds => ', that.newsReceiveUserIds);
            let _len = that.briefReceiveUserIds.length;
            if (_len > 0) {
                // 向表单中添加记录
                for (let i = 0; i < _len; i ++) {
                    let _receiveUserId = that.briefReceiveUserIds[i];
                    let _user = that.allSysUsersMap[_receiveUserId];
                    if (_user) {
                        that.formArticle.receiverIds.push(_receiveUserId);
                        that.formArticle.receiveUsers.push(_user);
                    }
                }
                that.formArticle.receiverIdArrStr = that.formArticle.receiverIds.join(",");
                console.log('(handleBriefAddReceivers)that.formArticle.receiverIds', that.formArticle.receiverIds);
                console.log('(handleBriefAddReceivers)that.formArticle.receiveUsers', that.formArticle.receiveUsers);
                that.$forceUpdate();
            }
            that.dialogShow.briefReceivers = !that.dialogShow.briefReceivers;
        },

        /**
         * (简报)移除接收人
         * @param _reUserId 用户ID
         */
        handleBriefRemoveReceiver: function(_reUserId) {
            _reUserId = _reUserId || '';
            let that = this;
            console.log('that.formArticle', that.formArticle);
            if (_reUserId && _reUserId != '') {
                let _len = that.formArticle.receiveUsers.length;
                let _ids = [], _users = [];
                if (_len > 0) {
                    for (let i = 0; i < _len; i ++) {
                        let __user = that.formArticle.receiveUsers[i];
                        if (__user.userId != _reUserId) {
                            _ids.push(__user.userId);
                            _users.push(__user);
                        }
                    }
                }
                that.formArticle.receiveUsers = _users;
                that.formArticle.receiverIds = _ids;
                that.formArticle.receiverIdArrStr = _ids.join(",");
                console.log('(handleBriefRemoveReceiver)that.formArticle.receiverIds', that.formArticle.receiverIds);
                that.$forceUpdate();
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
                    pageSize: pageSize,
                    categoryId: that.formSearchArticle.categoryId,
                    sendType: that.formSearchArticle.sendType,
                    creatorId: that.currentUser.userId,
                }})
                .then(function(response){/*成功*/
                	that.handleResponse(response);
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
         * 加载当前用户接收到的简报信息
         */
        loadCurrUserReceiverBriefRecord(criteria, pageNum, pageSize,categoryid) {
            let that = this;
            that.newsLoading=true;
            axios.get("/api/article/getCurrUserReceiverBriefRecord", {params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize: pageSize,
                    briefId: that.formSearchBriefSendRecord.briefId,
                    senderId: that.formSearchBriefSendRecord.senderId,
                    sendTime: that.formSearchBriefSendRecord.sendTime,
                    viewTime: that.formSearchBriefSendRecord.viewTime,
                    categoryId: categoryid
                }})
                .then(function(response){/*成功*/
                	that.handleResponse(response);
                	if(parseInt(response.status) == 200 ) {
                        that.currUserReceiverBriefRecords = response.data.data.list;
                        if(categoryid == null){
                        	if(pageSize == 3){
                            	that.currUserReceiverBriefRecordsfirstPage =response.data.data.list;
                            }
                        }else{
                        	if(pageSize == 3){
                            	that.currUserReceiverXxjlRecordsfirstPage =response.data.data.list;
                            }
                        }
                        that.pager.briefSendRecord.totalCount = response.data.data.total;
                    }
                	that.newsLoading=false;
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                    that.newsLoading=false;
                });
        },
        /**
         * 加载当前用户接收到的简报信息
         */
        loadCurrUserSendBriefRecord(criteria, pageNum, pageSize,categoryid) {
            let that = this;
            let sendflag = that.formSearchArticle.flag;
            if(sendflag == null){
            	sendflag = '';
            }
            that.newsLoading=true;
            axios.get("/api/article/getCurrUserReceiverBriefRecord", {params:{
                    key: criteria,
                    pageNum: pageNum,
                    pageSize: pageSize,
                    briefId: that.formSearchBriefSendRecord.briefId,
                    senderId: that.formSearchBriefSendRecord.senderId,
                    sendTime: that.formSearchBriefSendRecord.sendTime,
                    viewTime: that.formSearchBriefSendRecord.viewTime,
                    flag:sendflag,
                    categoryId: categoryid 
                }})
                .then(function(response){/*成功*/
                	that.handleResponse(response);
                	if(parseInt(response.status) == 200 ) {
                        that.currUserReceiverBriefRecords = response.data.data.list;
                        if(categoryid == null){
                        	if(pageSize == 3){
                            	that.currUserReceiverBriefRecordsfirstPage =response.data.data.list;
                            }
                        }else{
                        	if(pageSize == 3){
                            	that.currUserReceiverXxjlRecordsfirstPage =response.data.data.list;
                            }
                        }
                        that.pager.briefSendRecord.totalCount = response.data.data.total;
                    }
                	that.newsLoading=false;
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                    that.newsLoading=false;
                });
        },
      //每页显示数据量变更
        handleCurrUserReceiverBriefRecordSizeChange: function(val) {
            this.pager.briefSendRecord.pageSize = val;
            if(this.currentArticleFormTitle === '学习交流'){
            	if(this.receiveArtcleType =='info'){
            		this.loadCurrUserReceiverBriefRecord(this.pager.briefSendRecord.criteria, this.pager.briefSendRecord.currentPage, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');

            	}else{
                    this.loadCurrUserSendBriefRecord(this.pager.briefSendRecord.criteria, this.pager.briefSendRecord.currentPage, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');

            	}
            }else{
            	if(this.sendArtcleType =='info'){
                    this.loadCurrUserSendBriefRecord(this.pager.briefSendRecord.criteria, this.pager.briefSendRecord.currentPage, this.pager.briefSendRecord.pageSize,null);

            	}else{
                	this.loadCurrUserReceiverBriefRecord(this.pager.briefSendRecord.criteria, this.pager.briefSendRecord.currentPage, this.pager.briefSendRecord.pageSize,null);

            	}
            }
        },

        //页码变更
        handleCurrUserReceiverBriefRecordCurrentChange: function(val) {
            this.pager.briefSendRecord.currentPage = val;
            if(this.currentArticleFormTitle === '学习交流'){
            	if(this.receiveArtcleType =='info'){
            		this.loadCurrUserReceiverBriefRecord(this.pager.briefSendRecord.criteria, this.pager.briefSendRecord.currentPage, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');

            	}else{
                    this.loadCurrUserSendBriefRecord(this.pager.briefSendRecord.criteria, this.pager.briefSendRecord.currentPage, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');

            	}
            }else{
            	if(this.sendArtcleType =='info'){
                    this.loadCurrUserSendBriefRecord(this.pager.briefSendRecord.criteria, this.pager.briefSendRecord.currentPage, this.pager.briefSendRecord.pageSize,null);

            	}else{
                	this.loadCurrUserReceiverBriefRecord(this.pager.briefSendRecord.criteria, this.pager.briefSendRecord.currentPage, this.pager.briefSendRecord.pageSize,null);

            	}
            }
        },
        /**
         * index:0,
                name:'通知公告',
                url:'announce',
                modelName:'通知公告',
                imgUrl:'/images/icon/news.png'
         */
        moreAnnounce(){
        	let that = this;
        	that.showContent  = 'announce';
        	that.def_menu_id = 'announce';
        	let model = {};
        	model.moduleCode = 'announce';
        	model.moduleName = '通知公告';
        	that.handleAddTab2(model);
            that.$forceUpdate();
        },
        /**
         * {
                index:1,
                name:'党建动态',
                url:'articles',
                imgUrl:'/images/icon/brief.png',
                modelName:'党建动态'
            }
         */
        moreArticle(tab,name){
        	let that = this;
        	that.showContent  = tab;
        	that.def_menu_id = tab;
        	let model = {};
        	model.moduleCode = tab;
        	model.moduleName = name;
        	that.handleAddTab2(model);
            that.$forceUpdate();
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
                	that.handleResponse(response);
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
        nddyxxcjImportStart(){
        	let that = this;
        	that.dialogShow.nddyxxcjImport = true;
        	that.uploadFJList =[];
            that.uploadFJName = [];
            that.uploadFJUrl = [];
        },
        submitNddyxxcjExcel(){
        	let that = this;	
        	let params = new URLSearchParams();
        	
        	if(that.uploadFJUrl != null && that.uploadFJUrl.length >0){
            	params.append('filePath',that.uploadFJUrl.join(','));
        	}else{
        		that.$message.error("请选择文件");
        		return false;
        	}
        	
        	that.fullscreenLoading = true;
        	axios.post("/api/org/partyMemberExcelImport",params)
    		.then(function(response){
    			that.uploadFJList =[];
                that.uploadFJName = [];
                that.uploadFJUrl = [];
    			that.handleResponse(response);
    			if(parseInt(response.data.code) === 200){
                    that.$message({
                        message: '导入成功',
                        type: 'success'
                    });
                    that.dialogShow.nddyxxcjImport = false;
                }else if(parseInt(response.data.code) === 2000){
                	that.$message.error(response.data.msg);
                }
    			else{
                	that.$message.error("导入失败");
                }
    			that.fullscreenLoading = false;
    		}).catch(function(err){
    			that.fullscreenLoading = false;
    			that.$message.error("导入失败,请联系管理员");
            });
        },
        /**
         * 加载资源信息
         */
        loadResList(criteria, pageNum, pageSize) {
            let that = this;
            that.orgCommitteeLoading = true;
            axios.get("/api/res/search", {params:{
                    key: that.formSearchRes.key,
                    typeId: that.formSearchRes.typeId,
                    assId: that.formSearchRes.assId,
                    assTypeId: that.formSearchRes.assTypeId,
                    announcerId: that.formSearchRes.announcerId,
                    publishTime: that.formSearchRes.publishTime,
                    // yearMonth: orgId ? '': that.formSearchRes.yearMonth,
                    pageNum: pageNum,
                    pageSize: pageSize,
                    orgId: that.formSearchRes.orgId,
                }})
                .then(function(response){/*成功*/
                	that.handleResponse(response);
                	
                	if(parseInt(response.status) == 200 ) {
                        that.resArray = response.data.data.list;
                        that.pager.res.totalCount = response.data.data.total;
                    }
                	that.orgCommitteeLoading = false;
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                    that.orgCommitteeLoading = false;
                });
        },
        //每页显示数据量变更
        handleResSizeChange: function(val) {
            this.pager.res.pageSize = val;
            this.loadResList(this.pager.res.criteria, this.pager.res.currentPage, this.pager.res.pageSize);
        },

        //页码变更
        handleResCurrentChange: function(val) {
            this.pager.res.currentPage = val;
            this.loadResList(this.pager.res.criteria, this.pager.res.currentPage, this.pager.res.pageSize);
        },
        handleArtcleSearchByViewed(type){
        	let that = this;
        	if(type == '1'){
        		that.receiveArtcleType='info';
        		that.sendArtcleType='';
        		that.newsOperate=false;
        		that.operateName = '操作';
        		that.operateIcon = 'el-icon-setting';
        		that.currentArticleFormTitle = '简报';
                that.formSearchArticle.isBrief = true;
                that.formSearchArticle.sendType = '1';
                that.formSearchArticle.categoryId = '53c34dec-7447-4bbc-9ff3-af0f0686b07f';
                //that.loadArticles('',1, that.pager.article.pageSize);
                that.currAction = 'append';
                that.def_menu_id = 'articles';
                that.loadCurrUserReceiverBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,null);
        	}else if(type == '0'){
        		that.receiveArtcleType='';
        		that.sendArtcleType='info';
        		that.currentArticleFormTitle = '简报';
                that.formSearchArticle.isBrief = true;
                that.formSearchArticle.sendType = '1';
                that.formSearchArticle.categoryId = '53c34dec-7447-4bbc-9ff3-af0f0686b07f';
                that.formSearchArticle.flag='0';
                //that.loadArticles('',1, that.pager.article.pageSize);
                that.currAction = 'append';
                that.def_menu_id = 'articles';
                that.loadCurrUserSendBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,null);
        	}
        },
        handlexxjlSearchByViewed(type){
        	let that = this;
        	if(type == '1'){
        		//已收
        		that.receiveArtcleType='info';
        		that.sendArtcleType='';
        		that.toApprove='',
        		that.approveSuccess='',
        		that.approveFail='',
        		that.newsOperate=false;
        		that.operateName = '操作';
        		that.operateIcon = 'el-icon-setting';
        		that.currentArticleFormTitle = '学习交流';
                that.formSearchArticle.isBrief = true;
                that.formSearchArticle.sendType = '1';
                that.formSearchArticle.categoryId = '63c34dec-7447-4bbc-9ff3-af0f0686b07f';
                //that.loadArticles('',1, that.pager.article.pageSize);
                that.currAction = 'append';
                that.def_menu_id = 'xxjl';
                that.loadCurrUserReceiverBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
        	}else if(type == '0'){
        		//已发
        		that.receiveArtcleType='';
        		that.sendArtcleType='info';
        		that.toApprove='',
        		that.approveSuccess='',
        		that.approveFail='',
        		that.currentArticleFormTitle = '学习交流';
        		that.operateName = '操作';
        		that.operateIcon = 'el-icon-setting';
                that.formSearchArticle.isBrief = true;
                that.formSearchArticle.sendType = '1';
                that.formSearchArticle.categoryId = '63c34dec-7447-4bbc-9ff3-af0f0686b07f';
                that.formSearchArticle.flag='0';
                //that.loadArticles('',1, that.pager.article.pageSize);
                that.currAction = 'append';
                that.def_menu_id = 'xxjl';
                that.loadCurrUserSendBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
        	}else if(type == '2'){
        		//待审批
        		that.receiveArtcleType='';
        		that.sendArtcleType='';
        		that.toApprove='info',
        		that.approveSuccess='',
        		that.approveFail='',
        		that.currentArticleFormTitle = '学习交流';
        		that.newsOperate=false;
                that.formSearchArticle.isBrief = true;
                that.formSearchArticle.sendType = '1';
                that.formSearchArticle.categoryId = '63c34dec-7447-4bbc-9ff3-af0f0686b07f';
                that.formSearchArticle.flag='2';
                //that.loadArticles('',1, that.pager.article.pageSize);
                that.currAction = 'append';
                that.def_menu_id = 'xxjl';
                console.log(that.toApprove)
                that.loadCurrUserSendBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
        	}else if(type == '3'){
        		//审批通过
        		that.receiveArtcleType='';
        		that.sendArtcleType='';
        		that.toApprove='';
        		that.approveSuccess='info';
        		that.newsOperate=false;
        		that.approveFail='';
        		that.currentArticleFormTitle = '学习交流';
                that.formSearchArticle.isBrief = true;
                that.formSearchArticle.sendType = '1';
                that.formSearchArticle.categoryId = '63c34dec-7447-4bbc-9ff3-af0f0686b07f';
                that.formSearchArticle.flag='3';
                //that.loadArticles('',1, that.pager.article.pageSize);
                that.currAction = 'append';
                that.def_menu_id = 'xxjl';
                that.loadCurrUserSendBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
        	}else if(type == '4'){
        		//审批未通过
        		that.receiveArtcleType='';
        		that.sendArtcleType='';
        		that.toApprove='';
        		that.approveSuccess='';
        		that.newsOperate=false;
        		that.approveFail='info';
        		that.currentArticleFormTitle = '学习交流';
                that.formSearchArticle.isBrief = true;
                that.formSearchArticle.sendType = '1';
                that.formSearchArticle.categoryId = '63c34dec-7447-4bbc-9ff3-af0f0686b07f';
                that.formSearchArticle.flag='4';
                //that.loadArticles('',1, that.pager.article.pageSize);
                that.currAction = 'append';
                that.def_menu_id = 'xxjl';
                that.loadCurrUserSendBriefRecord(that.formSearchBriefSendRecord.key, 1, this.pager.briefSendRecord.pageSize,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
        	}
        },
        /**
         * 加载资源下载信息
         */
        loadResDlList(criteria, pageNum, pageSize) {
            let that = this;
            axios.get("/api/res/searchDl", {params:{
                    key: that.formSearchResDl.key,
                    pageNum: pageNum,
                    pageSize:pageSize,
                }})
                .then(function(response){/*成功*/
                	that.handleResponse(response);
                	if(parseInt(response.status) == 200 ) {
                        that.resDlArray = response.data.data.list;
                        that.pager.resDl.totalCount = response.data.data.total;
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },
        //每页显示数据量变更
        handleResDlSizeChange: function(val) {
            this.pager.resDl.pageSize = val;
            this.loadResDlList(this.pager.resDl.criteria, this.pager.resDl.currentPage, this.pager.resDl.pageSize);
        },

        //页码变更
        handleResDlCurrentChange: function(val) {
            this.pager.resDl.currentPage = val;
            this.loadResDlList(this.pager.resDl.criteria, this.pager.resDl.currentPage, this.pager.resDl.pageSize);
        },

        /*
        handleAddTab(targetName) {
            let newTabName = ++this.editableTabsOptions.tabIndex + '';
            let __closable = targetName === 'firstPage' ? false : true;
            this.editableTabsOptions.editableTabs.push({
                title: targetName,
                name: newTabName,
                content: '',
                closable: __closable,
            });
            this.editableTabsValue = newTabName;
        },
         */

        /**
         * 添加tab
         * @param _module 模块信息
         */
        handleAddTab2(_module) {
            let that = this;
            if (_module && _module.moduleName) {
                let targetName = _module.moduleName || '';
                let targetCode = _module.moduleCode || '';
                let _tab = that.editableTabsOptions.editableTabs.find(function(_t){ return _t.name == targetCode});
                if (targetName != '' && targetCode != '') {
                    if (!_tab) {
                        let __closable = targetCode === 'firstPage' ? false : true;
                        that.editableTabsOptions.editableTabs.push({
                            title: targetName,
                            name: targetCode,
                            content: '',
                            closable: __closable,
                        });
                    }
                    that.editableTabsOptions.editableTabsValue = targetCode;
                    that.showContent = that.editableTabsOptions.editableTabsValue;
                    that.$forceUpdate();
                }

            }
        },

        /**
         * 移除tab
         * @param targetName 名字
         */
        handleRemoveTab(targetName) {
            let that = this;
            console.log('targetName=>', targetName, that.editableTabsOptions);
            let tabs = that.editableTabsOptions.editableTabs;
            let activeName = that.editableTabsOptions.editableTabsValue;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                        let nextTab = tabs[index - 1] || tabs[index + 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }

            that.editableTabsOptions.editableTabsValue = activeName;
            that.editableTabsOptions.editableTabs = tabs.filter(tab => tab.name !== targetName);
            // let _tabLen = that.editableTabsOptions.editableTabs.length;
            console.log('handleRemoveTab => ', targetName, activeName, that.editableTabsOptions.editableTabsValue, that.editableTabsOptions.editableTabs);

            that.def_menu_id = activeName;
            that.showContent = activeName;
            that.$refs['menuRef'].activeIndex = activeName;
            that.$forceUpdate();
        },

        /**
         * 点击tab
         * @param tab tab信息
         * @param event 事件信息
         */
        handleClickTab(tab, event) {
            //console.log('handleClickTab', tab, event);
            let that = this;
            let moduleCode = tab.name || 'firstPage';
            that.showContent = moduleCode;
            that.def_menu_id = moduleCode;
            that.$forceUpdate();
        },
        goToMenu(item){
        	let that = this;
        	
        	
        	if(item.url == 'chat' ){
        		that.handleOpenChat();
        	//	window.open('http://www.awycjcdj.com:8090/a.html', 'chat', 'width=500,height=500');
        	}else if(item.url == 'pFile' ){
        		that.$message({
                    message: '功能建设中',
                    center:true,
                    type: 'info'
                });
        	}
        	else{
        		that.showContent  = item.url;
            	that.def_menu_id = item.url;
	        	let model = {};
	        	model.moduleCode = item.url;
	        	
	        	model.moduleName = item.modelName;
	        	that.handleAddTab2(model);
	            that.$forceUpdate();
        	}
        },
        /**
         * 上传移除操作
         * @param file 文件
         * @param fileList 文件列表
         */
        handleUploadRemove(file, fileList) {
            let that = this;
            console.log('handleUploadRemove', file, fileList, that.$refs.uploadRes.uploadFiles);
            if (that.currAction === 'edit' && that.$refs.uploadRes.uploadFiles.length === 0) {
                that.formRes.isShowFileOriginalName = true;
            }
        },

        /**
         * 上传时预览
         * @param file 文件
         */
        handleUploadPreview(file) {
            console.log('handleUploadPreview', file);
        },

        /**
         * 执行上传文件
         * @param files 文件组
         * @param fileList 文件列表
         */
        handleUploadExceed(files, fileList) {
            // this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            this.$message.warning(`当前限制选择多个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        /**
         * 执行上传文件
         * @param files 文件组
         * @param fileList 文件列表
         */
        handleUploadExceedNews(files, fileList) {
            // this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            this.$message.warning(`附件最多为5个，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        handleNddyxxcjExcelImport(files, fileList) {
            // this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            this.$message.warning(`单次只能导入单个数据文件`);
        },
        /**
         * 文件上传之前
         * @param file 文件
         * @param fileList 文件列表
         * @returns {*}
         */
        beforeUploadRemove(file, fileList) {
            let that = this;
            let originalName = '';
            if (fileList && fileList instanceof Array) {
                let _names = [];
                for (let i = 0; i < fileList.length; i ++) {
                    _names.push(fileList[i].name);
                }
                originalName = _names.join(";");
            }
            that.formRes.originalName = originalName;
            return that.$confirm(`确定移除 ${ file.name }？`);
        },
        beforeFJRemove(file, fileList) {
            let that = this;
            let originalName = '';
            if (fileList && fileList instanceof Array) {
                let _names = [];
                for (let i = 0; i < fileList.length; i ++) {
                    _names.push(fileList[i].name);
                }
                originalName = _names.join(";");
            }
            that.formRes.originalName = originalName;
            return true;
        },
        /**
         * 文件上传之前
         * @param file 文件
         * @param fileList 文件列表
         * @returns {*}
         */
        beforeUploadRemove2(file, fileList) {
            let that = this;
            return that.$confirm(`确定移除 ${ file.name }？`);
        },

        /**
         * 上传提交
         * @param isResDl 是否为资料下载,默认为false
         * @returns {boolean}
         */
        submitUpload(isResDl) {
            let that = this;
            isResDl = isResDl == true ? true : false;
            console.log('uploadFileList', that.uploadFileList, that.formRes);
            //this.$refs.uploadRes.submit();
            if (that.uploadData.type != 'res-dyfgzd' && that.uploadData.type != 'res-dzzfgzd'
                && that.uploadData.type != 'res-dffgzd' && that.uploadData.type != 'res-hjfgzd'
                && that.uploadData.type != 'res2-fzdy') {
                if (that.formRes.publishTime == '') {
                    this.$message.error('请选择发布日期!');
                    return false;
                }
            }
            if (!isResDl) {
                /*仅当'发展党员时'才进行其他分类判定*/
                if (that.uploadData.type === 'res2-fzdy') {
                    if (that.formRes.assTypeId == '') {
                        this.$message.error('请选择其他分类!');
                        return false;
                    }
                }
            }

            if (that.formRes.resName == '') {
                this.$message.error('请填写资料名称!');
                return false;
            }

            if (that.currAction === 'edit') {
                if (that.$refs.uploadRes.uploadFiles.length === 0) {
                    that.submitForm('formRes');
                }
                else {
                    that.$refs.uploadRes.submit();
                }
            }
            else {  // 新增
                console.log('uploadRes=>', that.$refs.uploadRes, that.$refs.uploadRes.uploadFiles);
                if (that.$refs.uploadRes.uploadFiles.length === 0) {
                    this.$message.error('请选择上传文件!');
                    return false;
                }
                else {
                    that.$refs.uploadRes.submit();
                }
            }

        },

        submitUpload2() {
            let that = this;
            console.log('uploadFileList', that.uploadFileList, that.formRes);
            if (that.$refs.uploadRes2.uploadFiles.length === 0) {
                this.$message.error('请选择上传文件!');
            }
            else {
                that.$refs.uploadRes2.submit();
            }

        },

        handleUpload2(entry) {
            let that = this;
            that.dialogShow.resUpload = !that.dialogShow.resUpload;
            if (that.$refs.uploadRes2 && that.$refs.uploadRes2.uploadFiles && that.$refs.uploadRes2.uploadFiles != undefined) {
                that.$refs.uploadRes2.uploadFiles = [];
                that.$refs.uploadRes2.clearFiles();
            }
            console.log('(handleUpload2)entry', entry)
            if (entry) {
                that.formRes = {
                    recordId: entry.recordId,
                    resName: entry.resName,
                    typeId: entry.typeId,
                    assId: entry.assId,
                    assTypeId: entry.assTypeId,
                    originalName: entry.originalName,
                    recordTime: entry.recordTime,
                    recordFlag: entry.recordFlag,
                    resSize: entry.resSize,
                    announcerId: entry.announcerId,
                    publishTime: entry.publishTime,
                    resAuthor: entry.resAuthor,
                    resSrc: entry.resSrc,
                    resIntro: entry.resIntro,
                    resDesc: entry.resDesc,
                    resTags: entry.resTags,
                    modifyTime: entry.modifyTime,
                    editorId: entry.editorId,
                    accessUrl: entry.accessUrl,
                    auditorId: entry.auditorId,
                    auditTime: entry.auditTime,
                    auditStatus: entry.auditStatus,
                    typeName: entry.typeName,
                    assTitle: entry.assTitle,
                    assTypeName: entry.assTypeName,
                    announcerName: entry.announcerName,
                    editorName: entry.editorName,
                    auditorName: entry.auditorName,
                    currName: entry.currName || '',
                    orgId: entry.orgId || '',
                    orgName: entry.orgName || '',
                };
            }
        },

        closeUpload2() {
            let that = this;
            that.dialogShow.resUpload = false;
            that.$refs.uploadRes2.clearFiles();
        },

        handleUploadSuccess(res, file, fileList) {
            console.log('handleUploadSuccess', res);
            let that = this;
            if (parseInt(res.code) === 200) {
                let data = res.data;
                that.formRes.originalName = data.fileName || '';
                that.formRes.currName = data.newFileName || '';
                that.formRes.accessUrl = data.destName || '';
                that.formRes.resSize = (data.size || 0) + '';
                that.formRes.attaContent = data.content || '';
                console.log('(handleUploadSuccess)that.formRes', that.formRes, data);
                if (that.formRes.originalName != '' && that.formRes.currName != '') {
                    that.submitForm('formRes');
                }
                else {
                    this.$message.error('请重新选择文件上传!');
                }
                /*
                that.$notify.success({
                    title: '成功',
                    message: `文件上传成功`
                });

                 */
            }
            else {
                that.$notify.error({
                    title: '错误',
                    message: `文件上传失败`
                });
            }

        },

        handleUploadSuccess2(res, file, fileList) {
            console.log('handleUploadSuccess', res);
            let that = this;
            if (parseInt(res.code) === 200) {
                let data = res.data;
                console.log('handleUploadSuccess2', data);
                // that.$notify.success({
                //     title: '成功',
                //     message: `文件上传成功`
                // });

                let entry = {
                    recordId: that.formRes.recordId || '',
                    originalName: data.fileName || '',
                    currName: data.newFileName || '',
                    accessUrl: data.destName || '',
                    resSize: (data.size || 0) + '',
                    attaContent: data.content || '',
                };

                that.uploadResAttachmentInfo(entry);
            }
            else {
                that.$notify.error({
                    title: '错误',
                    message: `文件上传失败`
                });
            }

        },

        handleUploadError(err, file, fileList) {
            console.log('handleUploadError', err);
            this.$notify.error({
                title: '错误',
                message: `文件上传失败`
            });
        },

        uploadFileChange(file, fileList) {
            let that = this;
            console.log('uploadFileChange', file);
            console.log('uploadFileChange', fileList);

            if (that.currAction === 'edit' && that.$refs.uploadRes.uploadFiles.length !== 0) {
                that.formRes.isShowFileOriginalName = false;
            }

            /*
            const isIMAGE = (file.raw.type === 'image/jpeg' || file.raw.type === 'image/jpg' || file.raw.type === 'image/png'|| file.raw.type === 'image/gif');
            const isLt1M = file.size / 1024 / 1024 < 1;

            if (!isIMAGE) {
                this.$message.error('上传文件只能是图片格式!');
                return false;
            }
            if (!isLt1M) {
                this.$message.error('上传文件大小不能超过 1MB!');
                return false;
            }
            let reader = new FileReader();
            reader.readAsDataURL(file.raw);
            reader.onload = function(e){
                //console.log(this.result)//图片的base64数据
            }
             */
        },

        /**
         * tabs关闭下拉菜单
         * @param command 命令
         */
        handleTabsDropdown(command) {
            let that = this;
            console.log('handleTabsDropdown', command)
            command = command || 'all';
            let tabs = that.editableTabsOptions.editableTabs;
            let moduleCode = 'firstPage';

            if (command === 'all' || tabs.length == 1) {
                that.editableTabsOptions.editableTabs = tabs.filter(tab => tab.name === moduleCode);
            }
            else {
                if (that.editableTabsOptions.editableTabsValue === moduleCode) {
                    that.editableTabsOptions.editableTabs = tabs.filter(tab => tab.name === moduleCode);
                }
                else {
                    moduleCode = that.editableTabsOptions.editableTabsValue;
                    that.editableTabsOptions.editableTabs = tabs.filter(tab => (tab.name === moduleCode || tab.name === 'firstPage'));
                }
            }
            that.def_menu_id = moduleCode;
            that.showContent = moduleCode;
            that.$refs['menuRef'].activeIndex = moduleCode;
            that.editableTabsOptions.editableTabsValue = moduleCode;
            that.$forceUpdate();
        },

        /**
         * Header关闭下拉菜单
         * @param command 命令
         */
        handleHeaderDropdown(command) {
            let that = this;
            console.log('handleHeaderDropdown', command)
            command = command || '';
            if (command === 'modifyPwd') {
                //that.$message.error('功能创建中，请骚等!');
                // return false;
                that.dialogShow.modifyPwd = !that.dialogShow.modifyPwd;
            }
            if(command === 'logout') {
                document.getElementById('logoutForm').submit();
                return false;
            }
        },
        nddyxxcjExampleDownload(){
        	window.location.href = "/api/file/dl/template.xlsx"
        },
        nddyxxcjExcelDownload(){
        	let that = this;
        	that.$confirm('是否导出当前用户可见所有党员？', '警告', {
                confirmButtonText: '是',
                cancelButtonText: '否',
                type: 'warning',
                callback: action => {
                    if(action === "cancel"){
                        that.$message({
                            type: 'info',
                            message: "取消导出"
                        });
                    }else{
                    	
                    	window.location.href = "/api/org/nddyxxcjExcelExport";
                    	that.$message({
                            type: 'success',
                            message: "正在导出，请等待..."
                        });
                    }
                }
        	});
        	
        },
        dwjbxxExcelDownload(){
        	let that = this;
        	that.$confirm('是否导出当前用户可见所有党委基本信息？', '警告', {
                confirmButtonText: '是',
                cancelButtonText: '否',
                type: 'warning',
                callback: action => {
                    if(action === "cancel"){
                        that.$message({
                            type: 'info',
                            message: "取消导出"
                        });
                    }else{
                    	let url = "/api/org/dwjbxxExcelExport";
                    	if(that.isSuperAdmin){
                    		url = url +"?isSuperAdmin=1"
                    	}
                    	window.location.href = url;
                    	that.$message({
                            type: 'success',
                            message: "正在导出，请等待..."
                        });
                    }
                }
        	});
        	
        },
        /**
         * 下载资源
         * @param res
         */
        handleDownloadRes(res) {
            console.log('资源信息', res);
            let that = this;
            if (res) {
                window.open('/api/file/dlRes/' + that.uploadData.type + '/'
                    + res.currName + '/' + (res.originalName || res.currName));
            }
        },

        /**
         * 预览资源
         * @param res
         */
        handleViewRes(res) {
            console.log('资源信息（handleViewRes）', res);
            //let that = this;
            if (res) {
                let _url = window.location.protocol + '//' + window.location.host;
                if (!_url.endsWith('/')) {
                    _url += '/';
                }

                let _accessUrl = (res.accessUrl || '').replace(/\\/g, "/");
                if (_accessUrl.startsWith('/')) {
                    _url += _accessUrl.substring(1);
                }
                else {
                    _url += _accessUrl;
                }

                // 如果是office文档特殊处理
                let _accessUrl2 = _accessUrl.toLowerCase();
                if (_accessUrl2.endsWith('.ppt') || _accessUrl2.endsWith('.pptx') || _accessUrl2.endsWith('.xls')
                    || _accessUrl2.endsWith('.xlsx') || _accessUrl2.endsWith('.doc') || _accessUrl2.endsWith('.docx')
                    || _accessUrl2.endsWith('.xltx') || _accessUrl2.endsWith('.ppsx') || _accessUrl2.endsWith('.sldx')
                    || _accessUrl2.endsWith('.dotx') || _accessUrl2.endsWith('.xlsm') || _accessUrl2.endsWith('.xlsb')) {
                    // https://view.officeapps.live.com/op/view.aspx?src=http://www.tanwenfang.com./file/176bd4cb-e893-4dde-b20e-a98bb836b07e.docx
                        _url = "https://view.officeapps.live.com/op/view.aspx?src=" + _url;
                        window.open(_url)
                }
                else {
                    window.open(_url);
                }
            }
        },

        /**
         * 过滤发送者信息
         * @param value
         * @param data
         * @returns {boolean}
         */
        filterNodeOfReceiverIds(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1 || data.title.indexOf(value) !== -1;
        },
        /**
         * (选择接收者)选择节点事件
         */
        handleCheckChangeOfReceiverIds(data, checked, indeterminate) {
            console.log('handleCheckChangeOfReceiverIds=>', data, checked, indeterminate);
        },
        /**
         * (选择接收者)点击节点事件
         */
        handleNodeClickOfReceiverIds(data) {
            console.log('handleNodeClickOfReceiverIds=>', data);
        },

        /**
         * (选择接收者)提交发送着选择
         */
        submitReceiverUserOfReceiverIds() {
            console.log('submitReceiverUserOfReceiverIds')
        },

        /**
         * 基于ECharts的图表
         * @param chartId 图表显示位置的id
         * @param title 标题
         * @param subtext 子标题
         * @param legendData legend数据
         * @param seriesData 详细(series)数据
         */
        initEChartsPieChart(chartId, title, subtext, legendData, seriesData) {
            console.log('initEChartsPieChart', chartId);
            let that = this;
            let option = {
                color: ['#ccaf71', '#B2453F'],
                animation: 'auto',
                animationDuration: () => 0,
                title : {
                    text: title || '',
                    subtext: subtext || '',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c}人 (占比{d}%)",
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        restore: {show: true},
                        /*saveAsImage: {show: true}*/
                    }
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: legendData || []
                },
                series : [
                    {
                        name: '汇总',
                        type: 'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data: seriesData || [],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        },
                        label: {
                            normal: {
                                formatter: '{c} 占 {d}%'
                            }
                        }
                    }
                ]
            };
            try {
                let _charObj = document.getElementById(chartId);
                console.log('_charObj', _charObj);
                let myChart = echarts.init(_charObj);
                myChart.setOption(option, true);
                let chartObj = that.charts.find(function(m){ return m.id == chartId});
                if (chartObj) {
                    chartObj.chart = myChart;
                    return false;
                }
            } catch (e) {
                console.error(e)
            }
        },

        /**
         * 构建柱状图
         * @param chartId 图表ID
         * @param title 标题
         * @param subtext 子标题
         * @param legendData legend数据
         * @param xAxisData 横坐标数据
         * @param seriesData series数据
         */
        initEChartsCloumnChart(chartId, title, subtext, legendData, xAxisData, seriesData) {
            legendData = legendData || [];
            let posList = [
                'left', 'right', 'top', 'bottom',
                'inside',
                'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
                'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
            ];
            let app = {};
            app.configParameters = {
                rotate: {
                    min: -90,
                    max: 90
                },
                align: {
                    options: {
                        left: 'left',
                        center: 'center',
                        right: 'right'
                    }
                },
                verticalAlign: {
                    options: {
                        top: 'top',
                        middle: 'middle',
                        bottom: 'bottom'
                    }
                },
                position: {
                    options: echarts.util.reduce(posList, function (map, pos) {
                        map[pos] = pos;
                        return map;
                    }, {})
                },
                distance: {
                    min: 0,
                    max: 100
                }
            };

            app.config = {
                rotate: 90,
                align: 'left',
                verticalAlign: 'middle',
                position: 'insideBottom',
                distance: 15,
                onChange: function () {
                    let labelOption = {
                        normal: {
                            rotate: app.config.rotate,
                            align: app.config.align,
                            verticalAlign: app.config.verticalAlign,
                            position: app.config.position,
                            distance: app.config.distance
                        }
                    };
                    myChart.setOption({
                        series: [{
                            label: labelOption
                        }, {
                            label: labelOption
                        }, {
                            label: labelOption
                        }, {
                            label: labelOption
                        }]
                    });
                }
            };


            let labelOption = {
                show: true,
                position: app.config.position,
                distance: app.config.distance,
                align: app.config.align,
                verticalAlign: app.config.verticalAlign,
                rotate: app.config.rotate,
                formatter: '{c}  {name|{a}}',
                fontSize: 16,
                rich: {
                    name: {
                        textBorderColor: '#fff'
                    }
                }
            };

            let _seriesData = [];
            seriesData = seriesData || [];
            if (seriesData && seriesData.length > 0 && legendData.length == seriesData.length) {
                for (let i = 0; i < seriesData.length; i ++) {
                    let _tData = seriesData[i];
                    _seriesData.push({
                        name: legendData[i] || '',
                        type: 'bar',
                        barGap: 0,
                        label: labelOption,
                        data: _tData || []
                    });
                }
            }

            let barOption3 = {
                title : {
                    text: title || '',
                    subtext: subtext || '',
                    x:'center'
                },
                color: ['#003366', '#006699', '#4cabce', '#e5323e'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    y: 'bottom',
                    data: legendData || []
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        /*dataView: {show: true, readOnly: false},*/
                        magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                        restore: {show: true},
                        /*saveAsImage: {show: true}*/
                    }
                },
                xAxis: [
                    {
                        type: 'category',
                        axisTick: {show: false},
                        data: xAxisData || []
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: _seriesData
            };
            let myChart = echarts.init(document.getElementById(chartId));
            myChart.setOption(barOption3, true);
            let that = this;
            let chartObj = that.charts.find(function(m){ return m.id == chartId});
            if (chartObj) {
                chartObj.chart = myChart;
                return false;
            }
        },

        /**
         * 构建柱状图
         * @param chartId 图表ID
         * @param title 标题
         * @param subtext 子标题
         * @param legendData legend数据
         * @param xAxisData 横坐标数据
         * @param seriesData series数据
         */
        initEChartsCloumnChart2(chartId, title, subtext, legendData, xAxisData, seriesData) {
            let labelOption = {
                normal: {
                    show: true,
                    position: 'insideBottom',
                    rotate: 90,
                    textStyle: {
                        align: 'left',
                        verticalAlign: 'middle'
                    }
                }
            };
            let options = {
                color: ['#ccaf71', '#B2453F'],
                title : {
                    text: title || '',
                    subtext: subtext || '',
                    x:'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    y: 'bottom',
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        /*dataView: {show: true, readOnly: false},*/
                        magicType: {show: true, type: ['line', 'bar', 'tiled']},
                        restore: {show: true},
                        /*saveAsImage: {show: true}*/
                    }
                },
                xAxis: {
                    type: 'category',
                    data: xAxisData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: seriesData,
                    type: 'bar',
                    label: labelOption,
                }]
            };
            let myChart = echarts.init(document.getElementById(chartId));
            myChart.setOption(options, true);
            let that = this;
            let chartObj = that.charts.find(function(m){ return m.id == chartId});
            if (chartObj) {
                chartObj.chart = myChart;
                return false;
            }
        },

        /**
         * 构建柱状图
         * @param chartId 图表ID
         * @param title 标题
         * @param subtext 子标题
         * @param legendData legend数据
         * @param xAxisData 横坐标数据
         * @param seriesData series数据
         */
        initEChartsCloumnChart3(chartId, title, subtext, legendData, xAxisData, seriesData) {
            let labelOption = {
                normal: {
                    show: true,
                    position: 'insideBottom',
                    rotate: 90,
                    textStyle: {
                        align: 'left',
                        verticalAlign: 'middle'
                    }
                }
            };
            let options = {
                xAxis: {
                    type: 'category',
                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: [120, 200, 150, 80, 70, 110, 130],
                    type: 'bar',
                    showBackground: true,
                    backgroundStyle: {
                        color: 'rgba(220, 220, 220, 0.8)'
                    }
                }]
            };
            let myChart = echarts.init(document.getElementById(chartId));
            myChart.setOption(options);
            let that = this;
            let chartObj = that.charts.find(function(m){ return m.id == chartId});
            if (chartObj) {
                chartObj.chart = myChart;
                return false;
            }
        },

        initEChartsCloumnChart4() {
            let labelOption = {
                normal: {
                    show: true,
                    position: 'insideBottom',
                    rotate: 90,
                    textStyle: {
                        align: 'left',
                        verticalAlign: 'middle'
                    }
                }
            };
            let options = {
                title : {
                    text: '测试柱状图',
                    subtext: 'XXX党委',
                    x:'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {},
                xAxis: {
                    data: ['1','2','3','4','5','6']
                },
                yAxis: [{
                    min: 5000000
                }, {
                }],
                grid: {
                    left: 100
                },
                series: [{
                    //name: 'bar',
                    type: 'bar',
                    label: labelOption,
                    data: [5590986,5644154,5682473,5709709,5748763,5770601],
                    animation: true
                }]
            };
            let chartId = 'bigDataChartId6';
            let myChart = echarts.init(document.getElementById(chartId));
            myChart.setOption(options, true);
            let that = this;
            let chartObj = that.charts.find(function(m){ return m.id == chartId});
            if (chartObj) {
                chartObj.chart = myChart;
                return false;
            }
        },

        initEChartsCloumnChart5(tChart) {
            let posList = [
                'left', 'right', 'top', 'bottom',
                'inside',
                'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
                'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
            ];

            let app = {};
            app.configParameters = {
                rotate: {
                    min: -90,
                    max: 90
                },
                align: {
                    options: {
                        left: 'left',
                        center: 'center',
                        right: 'right'
                    }
                },
                verticalAlign: {
                    options: {
                        top: 'top',
                        middle: 'middle',
                        bottom: 'bottom'
                    }
                },
                position: {
                    options: echarts.util.reduce(posList, function (map, pos) {
                        map[pos] = pos;
                        return map;
                    }, {})
                },
                distance: {
                    min: 0,
                    max: 100
                }
            };

            app.config = {
                rotate: 90,
                align: 'left',
                verticalAlign: 'middle',
                position: 'insideBottom',
                distance: 15,
                onChange: function () {
                    let labelOption = {
                        normal: {
                            rotate: app.config.rotate,
                            align: app.config.align,
                            verticalAlign: app.config.verticalAlign,
                            position: app.config.position,
                            distance: app.config.distance
                        }
                    };
                    myChart.setOption({
                        series: [{
                            label: labelOption
                        }, {
                            label: labelOption
                        }, {
                            label: labelOption
                        }, {
                            label: labelOption
                        }]
                    });
                }
            };


            let labelOption = {
                show: true,
                position: app.config.position,
                distance: app.config.distance,
                align: app.config.align,
                verticalAlign: app.config.verticalAlign,
                rotate: app.config.rotate,
                formatter: '{c}  {name|{a}}',
                fontSize: 16,
                rich: {
                    name: {
                        textBorderColor: '#fff'
                    }
                }
            };


            let _seriesData = [];
            if (tChart.seriesData) {
                let _seriesArr = tChart.seriesData || [];
                let _seriesLen = _seriesArr.length;
                if (_seriesLen > 0) {
                    for (let i = 0; i < _seriesLen; i ++) {
                        let _sData = _seriesArr[i];
                        console.log('_sData', _sData)
                        _seriesData.push({
                            name: _sData.name || '',
                            data: _sData.data || [],
                            type: 'bar',
                            barGap: 0,
                            label: labelOption,
                        })
                    }
                }
            }
            tChart.seriesData = _seriesData;
            console.log('tChart', tChart);

            let option = {
                title : {
                    text: tChart.title || '',
                    subtext: tChart.subtext || '',
                    x:'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    y: 'bottom',
                    data: tChart.legendData || []
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                        restore: {show: true},
                        /*saveAsImage: {show: true}*/
                    }
                },
                xAxis: [
                    {
                        type: 'category',
                        axisTick: {show: false},
                        data: tChart.axis || []
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: tChart.seriesData || []
            };
            let chartId = tChart.id;
            let myChart = echarts.init(document.getElementById(chartId));
            myChart.setOption(option, true);
        },

        /**
         * 简报接收人全选
         * @param val
         */
        handleArticleReceiversCheckAllChange(val) {
            let that = this,checkIds = [];

            for (let key in that.allSysUsersMap) {
                //let entry = taht.allSysUsersMap[key];
                checkIds.push(key);
            }
            console.log('handleArticleReceiversCheckAllChange', that.checkBoxOptions.article, that.allSysUsersMap, val, checkIds);
            that.briefReceiveUserIds = val ? checkIds : [];
            that.isIndeterminate.article = false;
            console.log('handleArticleReceiversCheckAllChange(briefReceiveUserIds)', that.briefReceiveUserIds)
        },

        /**
         * 简报选项选择
         * @param value
         */
        handleArticleReceiversCheckedChange(value) {
            let that = this;
            let checkedCount = value.length;
            that.checkBoxAll.article = (checkedCount === that.allSysUsersMap.length);
            that.isIndeterminate.article = checkedCount > 0 && checkedCount < that.allSysUsersMap.length;
            console.log('handleArticleReceiversCheckedChange(briefReceiveUserIds)', that.briefReceiveUserIds)
        },

        /**
         * 菜单折叠处理
         */
        handleMenuCollapse(e) {
            let that = this;
            //console.log('that.isCollapse', that.isCollapse);
            if (that.isCollapse) {
                that.isCollapse = !that.isCollapse;
                that.menuCollapseIcon = 'el-icon-s-fold';
                that.menuCollapseDivClass = 'menu-collapse-div';
                //console.log('that.menuCollapseDivClass', that.menuCollapseDivClass, that.menuCollapseIcon);
            }
            else {
                that.isCollapse = !that.isCollapse;
                that.menuCollapseIcon = 'el-icon-s-unfold';
                that.menuCollapseDivClass = 'menu-collapse-div2';
                //console.log('that.menuCollapseDivClass', that.menuCollapseDivClass, that.menuCollapseIcon);
            }
            that.$forceUpdate();
        },

        /**
         * 获得当前用户(大数据)图表数据
         */
        getCurrUserEchartsData() {
            let that = this;
            that.currUserEchartsData = {};
            axios.get("/api/org/getEchartsDataByCurrentUser")
                .then(function(response){/*成功*/
                	that.handleResponse(response);
                    let data = response.data;
                    //console.log('getCurrUserReceivedNewestNews.data ', data.data.list)
                    if(parseInt(data.code) === 200) {
                        that.currUserEchartsData = data.result || {};
                        console.log('that.currUserEchartsData', that.currUserEchartsData);
                    }
                })
                .catch(function(err){/*异常*/
                    console.log(err);
                });
        },

        /**
         * 处理401、500返回
         * @param response 响应信息
         */
        handleResponse(response) {
            //let that = this;
            let codeVal = parseInt(response.data.code);
            //let messageContent = '', messageType = 'info';
            switch (codeVal) {
                case 401:
                    window.location.href= window.location.protocol+'//'+window.location.host;
                    break;
            }
            /*that.$message({
                message: messageContent,
                type: messageType
            });*/
            return false;
        },

        /**
         * 获得当前年月，默认格式(yyyy-MM)
         */
        getCurrYearMonth() {
            let currDate = new Date();
            //currDate.setTime(currDate.getTime());
            let _month = currDate.getMonth()+1;
            _month = _month <= 9 ? '0' + _month : _month + '';
            let _day = currDate.getDate();
            _day = _day <= 9 ? '0' + _day : _day + '';
            //let currDateStr = currDate.getFullYear()+"-" + _month + "-" + _day;
            let currDateStr = currDate.getFullYear()+"-" + _month;
            console.log('getCurrYearMonth', currDateStr);
            return currDateStr;
        },

        /**
         * 大数据组织选择
         * @param val
         */
        handleBigDataChange(val) {
            let that = this;
            // console.log('handleBigDataChange.val', val, that.currUserEchartsData)
            if (that.currUserLegendArr.length > 1) {
                // console.log('handleBigDataChange做汇总处理', '当前选择=>', val);
                if (val === '全部') {
                    let sexChart = {
                        id: 'bigDataChartId',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        seriesData: [],
                    };
                    if (that.currUserEchartsData['sex']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['sex']));
                        console.log('sex._tChartData', _tChartData);
                        sexChart.title = _tChartData.title || '';
                        sexChart.subtitle = val;
                        let _legendArr = _tChartData.axis || [];
                        sexChart.legendData = _legendArr;
                        let _seriesData = [];
                        if (_tChartData.data) {
                            let _seriesArr = _tChartData.data || [];
                            let _sLen = _seriesArr.length;
                            if (_sLen > 0) {
                                let sData0 = 0, sData1 = 0;
                                for (let i = 0; i < _sLen; i ++) {
                                    let _seData = _seriesArr[i].data;
                                    sData0 = sData0 + (_seData[0] ? parseInt(_seData[0]) : 0);
                                    sData1 = sData1 + (_seData[1] ? parseInt(_seData[1]) : 0);
                                }
                                _seriesData = [
                                    {value: parseInt(sData0), name: _legendArr[0]},
                                    {value: parseInt(sData1), name: _legendArr[1]}
                                ];
                            }
                        }
                        sexChart.seriesData = _seriesData;
                        console.log('sex.sexChart', sexChart);
                        that.initEChartsPieChart(sexChart.id, sexChart.title,
                            sexChart.subtitle, sexChart.legendData, sexChart.seriesData);
                    }

                    let ageChart = {
                        id: 'bigDataChartId2',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: [],
                    };
                    if (that.currUserEchartsData['age']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['age']));
                        ageChart.title = _tChartData.title || '';
                        ageChart.subtitle = val;
                        ageChart.xAxisData = _tChartData.axis || [];
                        ageChart.legendData = [val];
                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        let _sLen = _sDataArr.length;
                        if (_sLen > 0) {
                            let sData = [];
                            for (let i = 0; i < _sLen; i ++) {
                                let _seData = _sDataArr[i].data;
                                if ( i == 0) {
                                    sData = _seData;
                                }
                                else {
                                    for (let j = 0; j < _seData.length; j ++) {
                                        sData[j] = parseInt(sData[j]) + parseInt(_seData[j]);
                                    }
                                }
                            }
                            _seriesData = sData;
                        }
                        ageChart.seriesData = _seriesData;
                        that.initEChartsCloumnChart2(ageChart.id, ageChart.title, ageChart.subtitle,
                            ageChart.legendData, ageChart.xAxisData, ageChart.seriesData);
                    }

                    let partyAgeChart = {
                        id: 'bigDataChartId3',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: []
                    };
                    if (that.currUserEchartsData['partyAge']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['partyAge']));
                        partyAgeChart.title = _tChartData.title || '';
                        partyAgeChart.subtitle = val;
                        partyAgeChart.xAxisData = _tChartData.axis || [];
                        partyAgeChart.legendData = [val];
                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        let _sLen = _sDataArr.length;
                        if (_sLen > 0) {
                            let sData = [];
                            for (let i = 0; i < _sLen; i ++) {
                                let _seData = _sDataArr[i].data;
                                if ( i == 0) {
                                    sData = _seData;
                                }
                                else {
                                    for (let j = 0; j < _seData.length; j ++) {
                                        sData[j] = parseInt(sData[j]) + parseInt(_seData[j]);
                                    }
                                }
                            }
                            _seriesData = sData;
                        }
                        partyAgeChart.seriesData = _seriesData;
                        that.initEChartsCloumnChart2(partyAgeChart.id, partyAgeChart.title, partyAgeChart.subtitle,
                            partyAgeChart.legendData, partyAgeChart.xAxisData, partyAgeChart.seriesData);
                    }

                    let educationChart = {
                        id: 'bigDataChartId4',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: [],
                    };
                    if (that.currUserEchartsData['education']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['education']));
                        educationChart.title = _tChartData.title || '';
                        educationChart.subtitle = val;
                        educationChart.xAxisData = _tChartData.axis || [];
                        educationChart.legendData = [val];
                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        let _sLen = _sDataArr.length;
                        if (_sLen > 0) {
                            let sData = [];
                            for (let i = 0; i < _sLen; i ++) {
                                let _seData = _sDataArr[i].data;
                                if ( i == 0) {
                                    sData = _seData;
                                }
                                else {
                                    for (let j = 0; j < _seData.length; j ++) {
                                        sData[j] = parseInt(sData[j]) + parseInt(_seData[j]);
                                    }
                                }
                            }
                            _seriesData = sData;
                        }
                        educationChart.seriesData = _seriesData;
                        that.initEChartsCloumnChart2(educationChart.id, educationChart.title, educationChart.subtitle,
                            educationChart.legendData, educationChart.xAxisData, educationChart.seriesData);
                    }
                }
                else {
                    let sexChart = {
                        id: 'bigDataChartId',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        seriesData: [],
                    };
                    if (that.currUserEchartsData['sex']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['sex']));
                        console.log('sex._tChartData', _tChartData);
                        sexChart.title = _tChartData.title || '';
                        sexChart.subtitle = val;
                        let _legendArr = _tChartData.axis || [];
                        sexChart.legendData = _legendArr;
                        let _seriesData = [];
                        if (_tChartData.data) {
                            let _seriesArr = _tChartData.data || [];
                            if (_seriesArr.length > 0) {
                                let _seData = _seriesArr.filter(_serie => _serie.name == val);
                                console.log('_seData', _seData)
                                if (_seData) {
                                    let _sData = _seData[0].data || [];
                                    _seriesData = [
                                        {value: parseInt(_sData[0]), name: _legendArr[0]},
                                        {value: parseInt(_sData[1]), name: _legendArr[1]}
                                    ];
                                }
                            }
                        }
                        sexChart.seriesData = _seriesData;
                        console.log('sex.sexChart', sexChart);
                        that.initEChartsPieChart(sexChart.id, sexChart.title,
                            sexChart.subtitle, sexChart.legendData, sexChart.seriesData);
                    }

                    let ageChart = {
                        id: 'bigDataChartId2',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: [],
                    };
                    if (that.currUserEchartsData['age']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['age']));
                        ageChart.title = _tChartData.title || '';
                        ageChart.subtitle = val;
                        ageChart.xAxisData = _tChartData.axis || [];
                        ageChart.legendData = [val];
                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        if (_sDataArr.length > 0) {
                            let _sDArr = _sDataArr.filter(_serie => _serie.name == val);
                            if (_sDArr && _sDArr.length > 0) {
                                let _sData = _sDArr[0];
                                _seriesData = _sData['data'];
                            }
                        }
                        ageChart.seriesData = _seriesData;
                        that.initEChartsCloumnChart2(ageChart.id, ageChart.title, ageChart.subtitle,
                            ageChart.legendData, ageChart.xAxisData, ageChart.seriesData);
                    }

                    let partyAgeChart = {
                        id: 'bigDataChartId3',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: []
                    };
                    if (that.currUserEchartsData['partyAge']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['partyAge']));
                        partyAgeChart.title = _tChartData.title || '';
                        partyAgeChart.subtitle = val;
                        partyAgeChart.xAxisData = _tChartData.axis || [];
                        partyAgeChart.legendData = [val];
                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        if (_sDataArr.length > 0) {
                            let _sDArr = _sDataArr.filter(_serie => _serie.name == val);
                            if (_sDArr && _sDArr.length > 0) {
                                let _sData = _sDArr[0];
                                _seriesData = _sData['data'];
                            }
                        }
                        partyAgeChart.seriesData = _seriesData;
                        that.initEChartsCloumnChart2(partyAgeChart.id, partyAgeChart.title, partyAgeChart.subtitle,
                            partyAgeChart.legendData, partyAgeChart.xAxisData, partyAgeChart.seriesData);
                    }

                    let educationChart = {
                        id: 'bigDataChartId4',
                        title: '',
                        subtitle: '',
                        legendData: [],
                        xAxisData: [],
                        seriesData: [],
                    };
                    if (that.currUserEchartsData['education']) {
                        let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['education']));
                        educationChart.title = _tChartData.title || '';
                        educationChart.subtitle = val;
                        educationChart.xAxisData = _tChartData.axis || [];
                        educationChart.legendData = [val];
                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        if (_sDataArr.length > 0) {
                            let _sDArr = _sDataArr.filter(_serie => _serie.name == val);
                            if (_sDArr && _sDArr.length > 0) {
                                let _sData = _sDArr[0];
                                _seriesData = _sData['data'];
                            }
                        }
                        educationChart.seriesData = _seriesData;
                        that.initEChartsCloumnChart2(educationChart.id, educationChart.title, educationChart.subtitle,
                            educationChart.legendData, educationChart.xAxisData, educationChart.seriesData);
                    }
                }
            }
            else {
                console.log('handleBigDataChange不做汇总处理');
                let sexChart = {
                    id: 'bigDataChartId',
                    title: '',
                    subtitle: '',
                    legendData: [],
                    seriesData: [],
                };
                if (that.currUserEchartsData['sex']) {
                    let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['sex']));
                    console.log('_tChartData', _tChartData);
                    sexChart.title = _tChartData.title || '';
                    let _subtitle = '';
                    let _tSubTitleArr = _tChartData.legend || [];
                    let _legendLen = _tSubTitleArr.length;
                    if (_legendLen == 1) {
                        if (_tSubTitleArr.length > 0) {
                            _subtitle = _tSubTitleArr[0];
                        }
                        sexChart.subtitle = _subtitle;
                        let _legendArr = _tChartData.axis || [];
                        sexChart.legendData = _legendArr;
                        let _seriesData = [];
                        if (_tChartData.data) {
                            let _seriesArr = _tChartData.data || [];
                            if (_seriesArr.length > 0) {
                                let _sData = _seriesArr[0].data || [];
                                _seriesData = [
                                    {value: parseInt(_sData[0]), name: _legendArr[0]},
                                    {value: parseInt(_sData[1]), name: _legendArr[1]}
                                ];
                            }
                        }
                        sexChart.seriesData = _seriesData;

                        that.initEChartsPieChart(sexChart.id, sexChart.title,
                            sexChart.subtitle, sexChart.legendData, sexChart.seriesData);
                    }
                    else if (_legendLen > 1) {
                        sexChart.legendData = _tChartData.legend || [];
                        sexChart.axis = _tChartData.axis || [];
                        let _seriesData = [];
                        if (_tChartData.data) {
                            let _seriesArr = _tChartData.data || [];
                            let _seriesLen = _seriesArr.length;
                            if (_seriesLen > 0) {
                                for (let i = 0; i < _seriesLen; i ++) {
                                    let _sData = _seriesArr[i];
                                    _seriesData.push({
                                        name: _sData.name || '',
                                        data: _sData.data || [],
                                    })
                                }
                            }
                        }
                        sexChart.seriesData = _seriesData;
                        that.initEChartsCloumnChart5(sexChart);
                    }

                }
                let ageChart = {
                    id: 'bigDataChartId2',
                    title: '',
                    subtitle: '',
                    legendData: [],
                    xAxisData: [],
                    seriesData: [],
                };
                if (that.currUserEchartsData['age']) {
                    let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['age']));
                    ageChart.title = _tChartData.title || '';
                    let _tSubTitleArr = _tChartData.legend || [];
                    let _legendLen = _tSubTitleArr.length;
                    if (_legendLen == 1) {
                        let _subtitle = '';
                        if (_tSubTitleArr.length > 0) {
                            _subtitle = _tSubTitleArr[0];
                        }
                        ageChart.subtitle = _subtitle;
                        ageChart.xAxisData = _tChartData.axis || [];

                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        if (_sDataArr.length > 0) {
                            for (let i = 0; i < _sDataArr.length; i++) {
                                let _sData = _sDataArr[i];
                                _seriesData.push(_sData['data']);
                            }
                        }
                        ageChart.seriesData = _seriesData.length > 0 ? _seriesData[0] : [];
                        that.initEChartsCloumnChart2(ageChart.id, ageChart.title, ageChart.subtitle,
                            ageChart.legendData, ageChart.xAxisData, ageChart.seriesData);
                    }
                }

                let partyAgeChart = {
                    id: 'bigDataChartId3',
                    title: '',
                    subtitle: '',
                    legendData: [],
                    xAxisData: [],
                    seriesData: []
                };
                if (that.currUserEchartsData['partyAge']) {
                    let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['partyAge']));
                    partyAgeChart.title = _tChartData.title || '';
                    let _subtitle = '';
                    let _tSubTitleArr = _tChartData.legend || [];
                    let _legendLen = _tSubTitleArr.length;
                    if (_legendLen == 1) {
                        if (_tSubTitleArr.length > 0) {
                            _subtitle = _tSubTitleArr[0];
                        }
                        partyAgeChart.subtitle = _subtitle;
                        partyAgeChart.xAxisData = _tChartData.axis || [];

                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        if (_sDataArr.length > 0) {
                            for (let i = 0; i < _sDataArr.length; i++) {
                                let _sData = _sDataArr[i];
                                _seriesData.push(_sData['data']);
                            }
                        }
                        partyAgeChart.seriesData = _seriesData.length > 0 ? _seriesData[0] : [];

                        that.initEChartsCloumnChart2(partyAgeChart.id, partyAgeChart.title, partyAgeChart.subtitle,
                            partyAgeChart.legendData, partyAgeChart.xAxisData, partyAgeChart.seriesData);
                    }
                }

                let educationChart = {
                    id: 'bigDataChartId4',
                    title: '',
                    subtitle: '',
                    legendData: [],
                    xAxisData: [],
                    seriesData: [],
                };

                if (that.currUserEchartsData['education']) {
                    let _tChartData = JSON.parse(JSON.stringify(that.currUserEchartsData['education']));
                    educationChart.title = _tChartData.title || '';
                    let _subtitle = '';
                    let _tSubTitleArr = _tChartData.legend || [];
                    let _legendLen = _tSubTitleArr.length;
                    if (_legendLen == 1) {
                        if (_tSubTitleArr.length > 0) {
                            _subtitle = _tSubTitleArr[0];
                        }
                        educationChart.subtitle = _subtitle;
                        educationChart.xAxisData = _tChartData.axis || [];

                        let _seriesData = [];
                        let _sDataArr = _tChartData.data || [];
                        if (_sDataArr.length > 0) {
                            for (let i = 0; i < _sDataArr.length; i++) {
                                let _sData = _sDataArr[i];
                                _seriesData.push(_sData['data']);
                            }
                        }
                        educationChart.seriesData = _seriesData.length > 0 ? _seriesData[0] : [];

                        that.initEChartsCloumnChart2(educationChart.id, educationChart.title, educationChart.subtitle,
                            educationChart.legendData, educationChart.xAxisData, educationChart.seriesData);
                    }

                }
            }
            // if (val === '全部') { // 全部做汇总
            //     if (that.currUserLegendArr.length === 1) {  /*不做统计处理*/
            //         console.log('handleBigDataChange不做汇总处理');
            //     }
            //     else {
            //         console.log('handleBigDataChange做汇总处理');
            //     }
            // }
        },

        /**
         * 发展党员下拉(修改党员当前被发展状态)
         * @param command 命令
         */
        handleFzdyDropdown(command) {
            let that = this;
            command = command || '';
            console.log('handleFzdyDropdown.command', command);
            if (command !== '') {
                let arr = command.split(",");
                let step = arr[0];
                console.log("发展状态等级：" + arr[0] +", 用户ID：" + arr[1]);

            	that.fzdyLoading = true;
                axios.get("/api/preParty/changeStageByUserId",{params:{
                        userId: arr[1],
                        stage: arr[0]
                    }})
                    .then(function(response){/*成功*/
                        that.handleResponse(response);
                        let data = response.data;
                        if(parseInt(data.code) === 200) {
                        	that.loadfzdy(that.fzdyCurrentOrg);
                        }
                        that.fzdyLoading = false;
                    })
                    .catch(function(err){/*异常*/
                    	that.fzdyLoading = false;
                    });
            
            }
        },

        /**
         * 初始化WebSocket
         */
        initWebSocket: function() {
            let that = this;
            if ('WebSocket' in window) {
                ws = new WebSocket(that.wsObj.url);
            } else if ('MozWebSocket' in window) {
                ws = new MozWebSocket(that.wsObj.url);
            } else {
                ws = new SockJS(that.wsObj.url2);
            }
            ws.onopen = function(evnt) {
                heartCheck.start();
            };
            ws.onmessage = function(evnt) {
                heartCheck.reset();
                //$("#msgcount").prepend("<p>"+evnt.data+"</p>");
                /**
                 * 可以通过event.data(即返回结果为json，通过解析JSON)，来达到目的
                 */
                console.log('evnt.data', evnt.data);
                let msg = eval("("+evnt.data+")");

                let toName = msg["toName"],fromName = msg["fromName"],isJoin = msg["isJoin"],
                    whoStr = "", isHeart = msg['isHeart'] || false;
                if (isHeart) {
                    console.log('健康检查 => ', msg);
                }
                else {
                    if(isJoin == "1") { //加入房间
                        //....
                    }
                    else {
                        if(toName == fromName) {	//接收者就是发送者
                            whoStr = "<b style='color:red'>我</b>：";
                        }
                        else {
                            whoStr = "<b style='color:blue'>"+fromName+"</b>：";
                        }
                    }
                    // $("#userCount").val(msg["userCount"]);
                    // $("#msgcount").prepend("<p>"+whoStr+msg["msg"]+"</p>"+msg["date"]);
                    console.log('接收到的信息', whoStr);
                }

            };
            ws.onerror = function(evnt) {
                that.wsWebSocketReconnect();
            };
            ws.onclose = function(evnt) {
                that.wsWebSocketReconnect();
            }
        },

        /**
         * ws发送消息
         */
        wsSendMessage: function(e) {

        },

        /**
         * 创建webSocket
         * @param url 链接
         */
        wsCreateWebSocket: function(url) {
            let that = this;
            try {
                ws = new WebSocket(url);
            } catch (e) {
                that.reconnect(url);
            }
        },

        getCommitteeData(){
        	let that = this;
        	that.orgCommitteeLoading = true;
        	axios.get("/api/org/getOrgList",{params:{
        		isSuperAdmin:that.isSuperAdmin
            }}).then(function(response){
            	that.handleResponse(response);
            	if(parseInt(response.data.code) == 200 ){
        			let parentArr = response.data.result.filter(l => l.upperOrg === null);
        			that.committeeOrg = that.getTreeData(response.data.result, parentArr,true);
        			console.log('committeeOrg ====',that.committeeOrg);
        			that.orgCommitteeLoading = false;
        		}else{
        			that.$message.error('数据加载失败');
        			that.orgCommitteeLoading = false;
        		}
        	}).catch(function(err){/*异常*/
        		that.$message.error('请求失败');
        		that.orgCommitteeLoading = false;
                });
        },
        //导出
        handleExport () {
        	
        },

        /**
         * websocket重连
         * @param url 链接
         */
        wsWebSocketReconnect: function(url) {
            let that = this;
            if(that.wsObj.lockReconnect) return;
            that.wsObj.lockReconnect = true;
            //没连接上会一直重连，设置延迟避免请求过多
            setTimeout(function () {
                that.createWebSocket(url);
                that.wsObj.lockReconnect = false;
            }, 2000);
        },

        webChatSendHandle: function() {
            let messageList = document.getElementById("messageList");
            let messageBox = document.getElementById("messageBox");
            //messageList.innerHTML = messageList.innerHTML + "<br/>" + messageBox.innerHTML;
            console.log('messageBox.content', messageBox.innerHTML);
        },

        changeFriendsBgColor: function(_this) {
            // 获取所有兄弟节点
            let siblings  = [];
            let items = _this.parentNode.children;
            for (let i = 0; i < items.length; i ++) {
                let _item = items[i];
                if (_item !== _this) {
                    siblings.push(_item);
                }
            }
            _this.className = 'webchat-container-left_item item-active-bgcolor';
            //document.getElementById("currentFriendName").innerHTML = '';
            for (let i = 0; i < siblings.length; i ++) {
                siblings[i].className = 'webchat-container-left_item';
            }
        },

        openAndCloseChatHandle: function(e) {
            let that = this;
            if (that.wsObj.webChatMainClass == 'webchat-main-default') {
                that.wsObj.webChatMainClass == 'webchat-main-show'
            }
            else {
                that.wsObj.webChatMainClass == 'webchat-main-default'
            }
        },

        closeWebchatHandle: function(e) {
            let that = this;
            that.wsObj.webChatMainClass == 'webchat-main-default'
        },

        /**
         * 添加预备党员
         * @param e
         */
        addPrePartyMemeberSubmitHandle: function(e) {
            let that = this;
            
            let _userName = that.formPrePartyMemeber.userName.replace(/(^\s*)|(\s*$)/g, "");
            if (_userName == '') {
                that.$message({
                    message: data.msg,
                    center:true,
                    type: 'error'
                });
                return false;
            }
            else {
            	that.fzdyLoading = true;
            	that.dialogShow.prePartyMemeber = false;
            	let params = new URLSearchParams();
                params.append('orgId',that.fzdyCurrentOrg);
                params.append('userName',that.formPrePartyMemeber.userName);
            	axios.post("/api/preParty/save",params)
        		.then(function(response){
        			that.handleResponse(response);
        			if(parseInt(response.data.code) === 200){
        				
        				that.loadfzdy(that.fzdyCurrentOrg);
                        that.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                        
                    }else if(parseInt(response.data.code) === 2000){
                    	that.$message.error(response.data.msg);
                    	that.dialogShow.prePartyMemeber = true;
                    }
        			else{
                    	that.$message.error("添加失败");
                    	that.dialogShow.prePartyMemeber = true;
                    }
        			that.fzdyLoading = false;
        		}).catch(function(err){
        			that.fzdyLoading = false;
        			that.$message.error("添加失败,请联系管理员");
        			that.dialogShow.prePartyMemeber = true;
                });
            }
        },

        /**
         * 删除预备党员
         * @param e
         */
        removePrePartyMemeberSubmitHandle: function(prePartyMemeberId) {
            prePartyMemeberId = prePartyMemeberId || '';
            if(prePartyMemeberId != ''){
            	let that = this;
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
                        	that.fzdyLoading = true;
                            axios.get("/api/preParty/deletePrePartyById",{params:{
                                    userId: prePartyMemeberId
                                }})
                                .then(function(response){/*成功*/
                                    that.handleResponse(response);
                                    let data = response.data;
                                    if(parseInt(data.code) === 200) {
                                    	that.loadfzdy(that.fzdyCurrentOrg);
                                    }
                                    that.fzdyLoading = false;
                                })
                                .catch(function(err){/*异常*/
                                	that.fzdyLoading = false;
                                });
                        }
                    }
            	});
            
            }
        },

        /**
         * 获得字符串宽度
         * @param fontSize 字体大小
         * @param fontFamily 字体
         * @param text 文本或字符串
         * @returns {number}
         */
        getTextWidth: function(fontSize, fontFamily, text){
            var span = document.createElement("span");
            var result = {};
            result.width = span.offsetWidth;
            result.height = span.offsetHeight;
            span.style.visibility = "hidden";
            span.style.fontSize = fontSize;
            span.style.fontFamily = fontFamily;
            span.style.display = "inline-block";
            document.body.appendChild(span);
            if(typeof span.textContent != "undefined"){
                span.textContent = text;
            }else{
                span.innerText = text;
            }
            result.width = parseFloat(window.getComputedStyle(span).width) - result.width;
            result.height = parseFloat(window.getComputedStyle(span).height) - result.height;
            return result.width;
        },

        /**
         * 重置(发展党员)预备党员列表部分样式
         */
        resetPrePartyMembersStyle: function() {
            let prePartyMembers = document.getElementById("prePartyMembersDiv");
            let members = prePartyMembers;
            console.log('members', members);
        },
        /**
         * 获取当前组织机构数据
         */
        
        getCurrentData(data){
        	this.resTableView = true;
        	let orgId = data.orgId;
        	this.formSearchRes.orgId = data.orgId;
        	this.loadResList('', 1, this.pager.res.pageSize);
        	
        },

        /**
         * 发展党员（搜索）分类
         */
        fzdyListShowRadioGroupHandle(val) {
            let that = this;
            that.formSearchRes.assTypeId = val;
            that.loadResList('', 1, that.pager.res.pageSize);
        },

        /**
         * 聊天：好友列表项选择事件处理
         * @param __idx 序号
         */
        chatFriendItemChangeHandle: function(__idx) {
            let that = this;
            console.log('chatFriendItemChangeHandle', __idx);
            let __friends = that.currUserChat.friends;
            let __friendsLen = __friends.length;
            if (__friendsLen > 0) {
                for (let i = 0; i < __friendsLen; i ++) {
                    let __friend = __friends[i];
                    if (i == __idx) {
                        __friend.isCurrent = true;
                        // 重新设置当前窗口显示信息
                        that.currUserChat.currChatWindow.currentFriendName = __friend.userName;
                        // 重新加载当前窗口信息列表
                        // currUserChat.currChatWindow.msgList = ....
                    }
                    else {
                        __friend.isCurrent = false;
                    }
                }
            }
        },

        /**
         * 显示聊天处理
         * @param chatId
         */
        showWebChatHandle: function(chatId) {
            console.log('showWebchatHandle', chatId)
            let that = this;
            let webchat = document.getElementById(chatId);
            if (webchat.style.visibility == 'hidden') {
                webchat.style.visibility = 'visible';
            }
            else {
                webchat.style.visibility = 'hidden';
            }
        },

        /**
         * 聊天表情处理
         * @param emojiId 表情序号
         * @param emoji 表情信息
         */
        chatEmojiChangeHandle: function(emojiId, emoji) {
            let that = this;
            console.log('chatEmojiChangeHandle', emojiId, emoji);
            that.currUserChat.currChatWindow.currentMessageContent += emoji.emoji;
        },

        /**
         * 聊天：加载好友信息列表
         */
        loadChatUserFriends: function() {
            let that = this;
            let currUserId = 'feng';
            // 从服务端接口获取
            axios.get("/api/chat/findFriendsLastestMessage", {params:{
                    userId: currUserId
                }})
                .then(function(response){/*成功*/
                    that.handleResponse(response);
                    let data = response.data;
                    if(parseInt(data.code) === 200) {
                        let __data = data.data;
                        console.log('findFriendsLastestMessage.data', __data)
                        let __friends = [];
                        let __activeFriend = {
                            currentFriendUserId: '',
                            currentFriendType: '',
                            currentFriendName: '',
                            currentMessageContent: '',
                            msgList: [],
                        };
                        let myFriends = __data.friends || [];
                        let myActiveFriend = __data.activeFriend || [];
                        let myFriendsLen = myFriends.length;
                        if (myFriendsLen > 0) {
                            for (let i = 0; i < myFriendsLen; i ++) {
                                let myFriendMsgArr = [];
                                let myFriend = myFriends[i];
                                let _frendId = '', _friendName = '';
                                if (myFriend.senderId === currUserId) {
                                    _frendId = myFriend.receiverId || '';
                                    _friendName = myFriend.receiverName || '';
                                }
                                else if (myFriend.receiverId === currUserId) {
                                    _frendId = myFriend.senderId || '';
                                    _friendName = myFriend.senderName || '';
                                }
                                if (i == 0) {
                                    for (let j = 0; j < myActiveFriend.length; j ++) {
                                        let _myActiveFriendMsg = myActiveFriend[i];
                                        myFriendMsgArr.push({
                                            recordId: _myActiveFriendMsg.recordId,
                                            t: "1",
                                            msg: _myActiveFriendMsg.content || '',
                                            date: _myActiveFriendMsg.recordTime || '',
                                            receiverId: _myActiveFriendMsg.receiverId || '',
                                            receiverName: _myActiveFriendMsg.receiverName || '',
                                            receiverAvatar: '🌋',
                                            receiverAvatarUrl: '',
                                            senderId: _myActiveFriendMsg.senderId || '',
                                            senderName: _myActiveFriendMsg.senderName || '',
                                            senderAvatar: '🌋',
                                            senderAvatarUrl: '',
                                            isMe: _myActiveFriendMsg.senderId == currUserId,
                                        })
                                    }

                                    __activeFriend = {
                                        currentFriendUserId: _frendId || '',
                                        currentFriendType:  '1',
                                        currentFriendName: _friendName || '',
                                        currentMessageContent: myFriend.content || '',
                                        msgList: myFriendMsgArr || [],
                                    };
                                }
                                __friends.push({
                                    msgId: myFriend.recordId || '',
                                    userId : _frendId,
                                    userName: _friendName,
                                    avatar: '🌋',
                                    avatarUrl: '',
                                    date: myFriend.recordTime || '',
                                    msg: myFriend.content || '',
                                    isCurrent: i == 0,
                                    type: '1',  /*聊天类型：用户对用户*/
                                    msgArr: myFriendMsgArr,
                                });
                            }
                        }

                        that.currUserChat.friends = __friends;
                        that.currUserChat.currChatWindow = __activeFriend;

                        // 打开窗口
                        that.currUserChat.chatShow = true;
                        that.$forceUpdate();
                    }

                })
                .catch(function(err){/*异常*/

                });

            // let __friends = [
            //     {
            //         userId : '001',
            //         userName: '路人甲',
            //         avatar: '🌋',
            //         avatarUrl: '',
            //         date: '2020/04/14',
            //         msg: '你在么？',
            //         isCurrent: true,
            //         type: '1',  /*聊天类型：用户对用户*/
            //         msgArr: [   /*最新消息列表*/
            //             {
            //                 recordId: '00001',
            //                 t: "1",
            //                 msg: '你今天开心么？',
            //                 date: '2020/04/05',
            //                 receiverId: 'feng',
            //                 receiverName: 'jianbo',
            //                 receiverAvatar: '🌋',
            //                 receiverAvatarUrl: '',
            //                 senderId: 'test',
            //                 senderName: '路人甲',
            //                 senderAvatar: '🌋',
            //                 senderAvatarUrl: '',
            //                 isMe: false,
            //
            //             },
            //             {
            //                 recordId: '00001',
            //                 t: "1",
            //                 msg: 'sadfasdfsadfasdfasdfsadf？',
            //                 date: '2020/04/05',
            //                 receiverId: 'test',
            //                 receiverName: '路人甲',
            //                 receiverAvatar: '🌋',
            //                 receiverAvatarUrl: '',
            //                 senderId: 'feng',
            //                 senderName: 'jianbo',
            //                 senderAvatar: '🌋',
            //                 senderAvatarUrl: '',
            //                 isMe: true,
            //             },
            //         ],
            //     },
            //     {
            //         userId : '002',
            //         userName: '路人乙',
            //         avatar: '🌋',
            //         avatarUrl: '',
            //         date: '2020/04/14',
            //         msg: '你不在么？',
            //         isCurrent: false,
            //         type: '1',  /*聊天类型：用户对用户*/
            //         msgArr: [],
            //     },
            //     {
            //         userId : '003',
            //         userName: '路人丙',
            //         avatar: '🌋',
            //         avatarUrl: '',
            //         date: '2020/04/14',
            //         msg: '还是不在啊🍏？',
            //         isCurrent: false,
            //         type: '1',  /*聊天类型：用户对用户*/
            //         msgArr: [],
            //     },
            // ];
            //
            // let __activeFriend = {
            //     currentFriendUserId: '',
            //     currentFriendType: '',
            //     currentFriendName: '',
            //     currentMessageContent: '',
            //     msgList: [],
            // };
            //
            // for (let i = 0; i < __friends.length; i ++) {
            //     let __friend = __friends[i];
            //     if (i === 0) {  // 默认取最新的一条记录以及对应的好友信息
            //         __activeFriend = {
            //             currentFriendUserId: __friend.userId || '',
            //             currentFriendType: __friend.type || '',
            //             currentFriendName: __friend.userName || '',
            //             currentMessageContent: __friend.msg || '',
            //             msgList: __friend.msgArr || [],
            //         };
            //     }
            //     if (__friend.isCurrent === true) {  // 如果有当前好友，则重新设置当前正在聊天的好友
            //         __activeFriend = {
            //             currentFriendUserId: __friend.userId || '',
            //             currentFriendType: __friend.type || '',
            //             currentFriendName: __friend.userName || '',
            //             currentMessageContent: __friend.msg || '',
            //             msgList: __friend.msgArr || [],
            //         };
            //     }
            // }
            //
            // that.currUserChat.friends = __friends;
            // that.currUserChat.currChatWindow = __activeFriend;
            //
            // // 打开窗口
            // that.currUserChat.chatShow = true;
            // that.$forceUpdate();
        },
    },
    props: {

    },
    beforeCreate: function() {
        let that = this;
        axios.defaults.withCredentials = true;
        axios.get("/api/admin/main/struct")
            .then(function(response){/*成功*/
            	that.handleResponse(response);
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
                that.formRes = config.formRes;
                that.formResDl = config.formResDl;
                that.formModifyPwd = config.formModifyPwd;
                that.formBriefSendRecord = config.formBriefSendRecord;
                that.formPrePartyMemeber = config.formPrePartyMemeber;

                that.dialogShow = config.dialogShow;
                that.rules = config.rules;
                that.chatEmojis = config.chatEmojis;

                let __chatConfig = config.chatConfig;
                that.chatConfig = __chatConfig[__chatConfig.active];
                // console.log('that.chatConfig', that.chatConfig);

              //  console.log('rules,',that.rules);
                let searchForm = config.searchForm;
                that.formSearchAuthModule = searchForm.authModule;
                that.formSearchArticle = searchForm.article;
                that.formSearchCategory = searchForm.category;
                that.formSearchCategoryType = searchForm.categoryType;
                that.formSearchDict = searchForm.dict;
                that.formSearchLangConf = searchForm.langConf;
                that.formSearchSysUser = searchForm.sysUser;
                that.formSearchMemberUser = searchForm.memberUser;
                that.formSearchNews = searchForm.news;
                that.formSearchPartyDues = searchForm.partyDues;
                that.formSearchRes = searchForm.res;
                that.formSearchResDl = searchForm.resDl;
                that.formSearchBriefSendRecord = searchForm.briefSendRecord;
                that.formSearchPrePartyMemeber = searchForm.prePartyMemeber;

            })
            .catch(function(err){/*异常*/
                console.log(err);
            });
    },
    created: function () {
        let that = this;
        //this.loadArticleTypes();
        this.getCurrentUserInfo();
        //this.firstPageDyzlxz();
        this.formSearchArticle.sendType = '1';
        this.formSearchArticle.categoryId = '53c34dec-7447-4bbc-9ff3-af0f0686b07f';
        //that.loadArticles('',1, that.pager.article.pageSize);
        this.currAction = 'append';
        //this.def_menu_id = 'articles';
        this.loadCurrUserReceiverBriefRecord(this.formSearchBriefSendRecord.key, 1, 3,null);
        this.loadCurrUserReceiverBriefRecord(this.formSearchBriefSendRecord.key, 1, 3,'63c34dec-7447-4bbc-9ff3-af0f0686b07f');
        this.getOrgIdByUserId();
        this.getAreaTreeDict();
        this.getCurrUserEchartsData();
        
        
    },
    beforeMount: function() {
        let that = this;
        // this.getCurrentUserInfo();
        /*
        // axios.interceptors.response.use(function (response) {
        //     if (response && response.data) {
        //         let codeVal = parseInt(response.data.code);
        //         let messageContent = '', messageType = 'info';
        //         switch (codeVal) {
        //             case 200:
        //                 return response;
        //                 break;
        //             case 401:
        //                 messageContent = '请登录!';
        //                 messageType = 'warning';
        //                 that.$message({
        //                     message: messageContent,
        //                     type: messageType
        //                 });
        //                 return false;
        //                 break;
        //             case 411:
        //                 messageContent = '请求参数异常!';
        //                 messageType = 'warning';
        //                 that.$message({
        //                     message: messageContent,
        //                     type: messageType
        //                 });
        //                 return false;
        //                 break;
        //             case 555:
        //                 messageContent = '"'+response.data.data+'"与已有数据重复!';
        //                 messageType = 'warning';
        //                 that.$message({
        //                     message: messageContent,
        //                     type: messageType
        //                 });
        //                 return false;
        //                 break;
        //             default:
        //                 window.location.href = '/';
        //                 break;
        //         }
        //     }
        //     else {
        //         window.location.href = '/';
        //         return response;
        //     }
        //
        // }, function (error) {
        //     switch (error.response.status) {
        //         case 401:
        //             window.location.href = '/';
        //             return Promise.reject(error);
        //         case 404:
        //             window.location.href = '/';
        //             return Promise.reject(error);
        //         case 500:
        //             window.location.href = '/';
        //             return Promise.reject(error);
        //     }
        //     return Promise.reject(error);
        // });
         */
    },
    mounted: function () {
        let that = this;
        that.getYearMonths();
        that.getAllAuthRole();
        //that.getAllSysUsers();
        that.getAllSysUsersMap();
        that.getCurrUserReceivedNewestNews();
        //that.loadMemberUsers('','',0, that.pager.user.pageSize);
        that.ueditors.article = UE.getEditor('articleEditor', that.ueditorConfig);
        that.ueditors.article.addListener("ready", function () {
            that.ueditors.article.setContent('', false); // 确保UE加载完成后，放入内容
        });
        that.ueditors.article.addListener('blur', function(editor){
            that.formArticle.content = that.ueditors.article.getContent();
        });
        
    },
    destroyed: function() {
        this.ueditors.article.destroy();
    }
})
