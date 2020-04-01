new Vue({
    el: '#admin',
    data: {
        formLogon: {},
        rules: {},
        fullscreenLoading:false,
        smsFlag:false,
        haveSms:false
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
                                document.getElementById("logonForm").submit()
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


