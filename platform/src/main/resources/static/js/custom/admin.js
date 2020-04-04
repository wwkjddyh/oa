new Vue({
    el: '#admin',
    data: {
        formLogon: {},
        rules: {},
        fullscreenLoading:false,
        smsFlag:false,
        haveSms:false,
        seconds:'获取'
    },
    methods: {
        resetForm() {
            let that = this;
            that.formLogon.username = '';
            that.formLogon.password = '';
            that.formLogon.verifyCode = '';
        },
        validate(){
        	let that = this;
            let error = "";
            let _verifyCode = that.formLogon.verifyCode.replace(/(^\s*)|(\s*$)/g, "");
            let nameLen = that.formLogon.username.replace(/(^\s*)|(\s*$)/g, "").length,
            pwdLen = that.formLogon.password.length,
            verifyCodeLen = _verifyCode.length;
	        
	        if(verifyCodeLen === 0) {
	            error = "验证码不能为空";
	        }
	        else if(verifyCodeLen != 4) {
	            error = "验证码长度必须为: 4 (个字符)";
	        }
	
	        if(error == '') {
	        	smsFlag=true;
	        }
        },
        change(i){
        	let that = this;
        	if(i>0){
        		that.seconds = i;
        	}
        },
        getSms(){
        	let that = this;
        	axios.get("/api/auth/getSMS",{params:{
        		userId: that.formLogon.username
            }})
            .then(function(response){/*成功*/
                let data = response.data;
                if(parseInt(data.code) === 200) {
                	that.smsFlag=true;
                	that.seconds = 60;
                	var timer = setInterval(function () {
                		if(that.seconds == 0){
                			clearInterval(timer);
                			that.seconds='获取';
                			that.smsFlag=false;
                		}else{
                			that.seconds = that.seconds -1;
                		}
                    },1000);
                	that.$message({
                        message: '短信已发送至'+data.result+'手机，请注意查收',
                        center:true,
                        type: 'sucess'
                    });
                }else{
                	that.$message({
                        message: data.msg,
                        center:true,
                        type: 'error'
                    });
                }
            })
            .catch(function(err){/*异常*/
            	that.$message({
                    message: "获取失败",
                    center:true,
                    type: 'error'
                });
            });
        },
        login(that){
        	
        	/*let params = new URLSearchParams();
            params.append('userName', that.formLogon.username);
            if(that.formLogon.verifySms != null && that.formLogon.verifySms != ""){
            	params.append('verifySms', that.formLogon.verifySms);
            }*/
            
            axios.get("/api/auth/validateSMS", {params:{
            	userName: that.formLogon.username,
            	verifySms: that.formLogon.verifySms
            }})
                .then(function(response){
                	if(parseInt(response.data.code) === 200){
                		document.getElementById("logonForm").submit();
                    }else{
                    	that.fullscreenLoading = false;
                    	that.$message({
                            message: response.data.msg,
                            center:true,
                            type: 'error'
                        });
                    }
                }).catch(function(err){
                	that.fullscreenLoading = false;
                	that.$message({
                        message: '验证码校验异常',
                        center:true,
                        type: 'error'
                    });
            });
        },
        needSms(userName){
        	let that = this;
        	axios.get("/api/auth/needSms",{params:{
        		userId: userName
            }})
            .then(function(response){/*成功*/
                let data = response.data;
                if(parseInt(data.code) === 200) {
                	that.haveSms = data.result;
                }else{
                	that.haveSms = false;
                }
            })
            .catch(function(err){/*异常*/
            	that.haveSms = false;
            });
        },
        submitForm() {
            let that = this;
            let error = "";
            let _verifyCode = that.formLogon.verifyCode.replace(/(^\s*)|(\s*$)/g, "");
            let nameLen = that.formLogon.username.replace(/(^\s*)|(\s*$)/g, "").length,
                pwdLen = that.formLogon.password.length,
                verifyCodeLen = _verifyCode.length;
            if(nameLen <= 2 || nameLen >= 65) {
                error = "用户名长度范围：3～64 (个字符)";
            }
            else if(pwdLen <= 5 || pwdLen >= 65) {
                error = "密码长度范围：6~65 (个字符)";
            }
            else if(verifyCodeLen === 0) {
                error = "验证码不能为空";
            }
            else if(verifyCodeLen != 4) {
                error = "验证码长度必须为: 4 (个字符)";
            }

            if(error != '') {
                that.$message({
                    message: error,
                    center:true,
                    type: 'error'
                });
            }
            else {
                axios.get("/api/verify/getCode")
                    .then(function(response) {
                        let data = response.data;
                        
                        if(parseInt(data.code) === 200) {
                        	
                            if (data.data.code === _verifyCode.toUpperCase()) {
                            	that.fullscreenLoading = true;
                            	axios.get("/api/auth/needSms",{params:{
                            		userId: that.formLogon.username
                                }})
                                .then(function(response){/*成功*/
                                    let data = response.data;
                                    if(parseInt(data.code) === 200) {
                                    	that.haveSms = data.result;
                                    	if(data.result==true && that.formLogon.verifySms != null
                                    			&& that.formLogon.verifySms != ''){
                                    		//that.fullscreenLoading = true;
                                            //document.getElementById("logonForm").submit();
                                    		that.login(that);
                                    	}else if(data.result==false){
                                    		//that.fullscreenLoading = true;
                                            document.getElementById("logonForm").submit();
                                    		//that.login(that);
                                    	
                                    	
                                    	}else{
                                    		that.$message({
                                                message: '请进行短信验证',
                                                center:true,
                                                type: 'error'
                                            });
                                    		that.fullscreenLoading = false;
                                    	}
                                    }else{
                                    	that.fullscreenLoading = false;
                                    	that.haveSms = false;
                                    	that.$message({
                                            message: '服务异常',
                                            center:true,
                                            type: 'error'
                                        });
                                    }
                                    
                                })
                                .catch(function(err){/*异常*/
                                	that.haveSms = false;
                                	that.fullscreenLoading = false;
                                	that.$message({
                                        message: '服务异常',
                                        center:true,
                                        type: 'error'
                                    });
                                });
                            	
                            }
                            else {
                                that.$message({
                                    message: '验证码错误或失效，可刷新验证码',
                                    center:true,
                                    type: 'error'
                                });
                                return;
                            }
                        }
                        else {
                            that.$message({
                                message: '请刷新验证码',
                                center:true,
                                type: 'error'
                            });
                            return;
                        }
                    })
                    .catch(function(err) {/*异常*/
                        console.log(err);
                        that.$message({
                            message: '请刷新验证码',
                            center:true,
                            type: 'error'
                        });
                        return;
                    });
            }
        },
    },
    beforeCreate: function() {
        let that = this;
        axios.defaults.withCredentials = true;
        axios.get("/api/admin/struct")
            .then(function(response) {
                let config = response.data.formStructConfig;
                that.formLogon = config.formLogon;
                that.rules = config.rules;
            })
            .catch(function(err) {/*异常*/
                console.log(err);
            });
    },
    created: function () {
    },
})


/**
 * 刷新验证码
 * @param _targetId 目标id
 */
function refreshVerifyCode(_targetId) {
    let verifyImg = document.getElementById(_targetId);
    if (verifyImg) {
        verifyImg.src = "/api/verify/code?&timestamp=" + new Date();
    }
}


