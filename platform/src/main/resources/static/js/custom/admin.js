new Vue({
    el: '#admin',
    data: {
        formLogon: {},
        rules: {},
    },
    methods: {
        resetForm() {
            let that = this;
            that.formLogon.username = '';
            that.formLogon.password = '';
            that.formLogon.verifyCode = '';
        },
        submitForm() {
            let that = this;
            let error = "";
            let nameLen = that.formLogon.username.replace(/(^\s*)|(\s*$)/g, "").length,
                pwdLen = that.formLogon.password.length;
            if(nameLen <= 2 || nameLen >= 65) {
                error = "用户名长度范围：3～64 (个字符)";
            }
            else if(pwdLen <= 5 || pwdLen >= 65) {
                error = "密码长度范围：6~65 (个字符)";
            }
            if(error != '') {
                that.$message({
                    message: error,
                    center:true,
                    type: 'error'
                });
            }
            else {
                document.getElementById("logonForm").submit()
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
