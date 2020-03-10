/**
 * 管理系统平台
 */
const formStructConfig = {
    formSysUser: {
        id: '',
        name: '',
        nickname: '',
        oldPassword: '',
        password: '',
        passwordOrgi: '',
        typeId: '',
        isHeadhunting: '0',
        memberId: '',
        status: 1,
    },
    formUser: {
        id: '',
        name: '',
        nickname: '',
        oldPassword: '',
        password: '',
        passwordOrgi: '',
        typeId: '',
        isHeadhunting: '0',
        memberId: '',
        status: 1,
    },
    formUserType: {
        id: '',
        name: '',
        desc: '',
    },
    formUserDetail: {
        id: '',
        userId: '',
        desc: '',
        nation: '',
        province: '',
        city: '',
        address: '',
        telephone: '',
        fax: '',
        zip: '',
        msn: '',
        qq: '',
        email: '',
        wechat: '',
        alipay: '',
    },
    formMember: {
        id: '',
        memberTitle: '',
        memberDesc: '',
        minIntegral: '',
        maxIntegral: '',
        minAmount: '',
        maxAmount: '',
    },
    formAuthUserRole: {
        recordId: '',
        userId: '',
        userName: '',
        roleId: '',
        roleName: '',
    },
    formAuthRoleModule: {
        recordId: '',
        roleId: '',
        moduleId: '',
        roleName: '',
        moduleName: '',
        fullModuleName: '',
    },
    formAuthRole: {
        roleId: '',
        roleName: '',
        roleDesc: ''
    },
    formAuthModule: {
        moduleId: '',
        parentId: '',
        moduleName: '',
        moduleDesc: '',
        moduleUrl: '',
        isLeaf: '0',
        fullModuleName: '',
    },
    formAuthUserRoleModule: {
        userId: '',
        userName: '',
        userNickname: '',
        roleId: '',
        roleName: '',
        moduleId: '',
        parentModuleId: '',
        moduleName: '',
        fullModuleName: ''
    },
    formCategory: {
        recordId: '',
        typeId: '',
        typeName: '',
        name:'',
        desc:'',
        recordTime:'',
        flag:'',
    },
    formCategoryType: {
        recordId: '',
        name:'',
        desc:'',
        recordTime:'',
        flag:''
    },
    formDict: {
        dictId: '',
        dictType: '',
        dictName: '',
        dictRemark: '',
        recordTime: '',
        recordFlag: ''
    },
    formLangConf: {
        recordId: '',
        name: '',
        desc: '',
        recordTime: '',
        updateTime: '',
        updateUserId: '',
        recordFlag: ''
    },

    searchForm: {
        memberUser: {
            name: '',
            nickname: '',
            typeId: '',
            isHeadhunting: '0',
            memberId: '',
            status: '',
        },
        authUserRole: {
            recordId: '',
            userId: '',
            userName: '',
            roleId: '',
            roleName: '',
        },
        authRoleModule: {
            recordId: '',
            roleId: '',
            moduleId: '',
            roleName: '',
            moduleName: '',
            fullModuleName: '',
        },
        authRole: {
            roleId: '',
            roleName: '',
            roleDesc: ''
        },
        authModule: {
            moduleId: '',
            parentId: '',
            moduleName: '',
            moduleDesc: '',
            moduleUrl: '',
            isLeaf: '0',
            fullModuleName: '',
        },
        authUserRoleModule: {
            userId: '',
            userName: '',
            userNickname: '',
            roleId: '',
            roleName: '',
            moduleId: '',
            parentModuleId: '',
            moduleName: '',
            fullModuleName: ''
        },
        category: {
            recordId: '',
            typeId: '',
            typeName: '',
            name:'',
            desc:'',
            recordTime:'',
            flag:'',
        },
        categoryType: {
            recordId: '',
            name:'',
            desc:'',
            recordTime:'',
            flag:''
        },
        dict: {
            dictId: '',
            dictType: '',
            dictName: '',
            dictRemark: '',
            recordTime: '',
            recordFlag: ''
        },
        langConf: {
            recordId: '',
            name: '',
            desc: '',
            recordTime: '',
            updateTime: '',
            updateUserId: '',
            recordFlag: ''
        },
    },
    dialogShow: {
        authModule: false,
        role: false,
        roleAdd: false,
        memberUser: false,
        sysUser: false,
        roleModule: false,
        userRole: false,
        userRoleModule: false,
        category: false,
        categoryType: false,
        langConf: false,
        dwjbxx: false
    },
    rules: {
        username: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 3, max: 64, message: '长度在 3 到 64 个字符之间', trigger: 'blur' }
        ],
        nickname: [
            { required: true, message: '请输入昵称', trigger: 'blur' },
            { min: 3, max: 64, message: '长度在 3 到 64 个字符之间', trigger: 'blur' }
        ],
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 128, message: '长度在 6 到 128 个字符之间', trigger: 'blur' }
        ],
        title: [
            { required: true, message: '请填写标题', trigger: 'blur' },
            { min: 3, max: 512, message: '长度在 3 到 512 个字符之间', trigger: 'blur' }
        ],
    }
};

let StructMock = Mock.mock('/api/admin/main/struct', {
    'formStructConfig':formStructConfig,
});
