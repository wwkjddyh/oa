/**
 * 管理系统平台
 */
const formStructConfig = {

    formLogon: {
        username: '',
        password: '',
        verifyCode: '',
    },
    rules: {
        username: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 3, max: 64, message: '长度在 3 到 64 个字符之间', trigger: 'blur' }
        ],
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 128, message: '长度在 6 到 128 个字符之间', trigger: 'blur' }
        ],
        verifyCode: [
            { required: true, message: '请输入验证码', trigger: 'blur' },
            { min: 6, max: 128, message: '长度必须为4字符', trigger: 'blur' }
        ],
    }
};

let StructMock = Mock.mock('/api/admin/struct', {
    'formStructConfig':formStructConfig,
});