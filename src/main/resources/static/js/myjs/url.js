/**
 * Created by gaowenfeng on 2017/5/23.
 */
var IP = "127.0.0.1"
var PORT = "9090";
var PLAT = "gdSystem";

// var URL  = "http://"+IP+":"+PORT+"/"+PLAT;
var URL  = "";
/**
 * 获取管理员列表
 * @type {string}
 */
var OBTAIN_ADMINLIST_URL = URL+"adminController/selectAdminList";
/**
 * 删除管理员列表
 * @type {string}
 */
var DELETE_ADMIN_URL = URL+"adminController/deleteAdmin";
/**
 * 修改管理员列表
 * @type {string}
 */
var UPDATE_ADMIN_URL = URL+"adminController/updateAdmin";
/**
 * 添加管理员列表
 * @type {string}
 */
var ADD_ADMIN_URL = URL+"adminController/addAdmin";
/**
 * 获取学生列表
 * @type {string}
 */
var OBTIAN_STUDENTLIST_URL = URL+"studentController/studentSelectList";
/**
 * 删除学生信息
 * @type {string}
 */
var DELETE_STUDENT_URL = URL+"studentController/studentDelete";
/**
 * 修改学生信息
 * @type {string}
 */
var UPDATE_STUDENT_URL = URL+"studentController/studentUpdate";
/**
 * 添加学生信息
 * @type {string}
 */
var ADD_STUDENT_URL = URL+"studentController/studentAdd";
/**
 * 获取教师列表
 * @type {string}
 */
var TEACHERLIST_URL = URL+"teacherController/teacherSelectList";
/**
 * 添加教师
 * @type {string}
 */
var ADDTEACHER_URL = URL+"teacherController/teacherAdd";
/**
 * 编辑教师
 * @type {string}
 */
var UPDATETEACHER_URL = URL+"teacherController/teacherUplate";
/**
 * 删除教师
 * @type {string}
 */
var DELETETEACHER_URL = URL+"teacherController/teacherDelete";
/**
 * 获取教师键值对信息
 * @type {string}
 */
var TEACHER_KVLIST_URL = URL+"teacherController/teacherKVSelectList";
/**
 * 获取班级列表
 * @type {string}
 */
var GETCLASS_URL = URL+"classController/getClasses";
/**
 * 学生列表中获取班级信息
 * @type {string}
 */
var GETCLASSSELECT_URL =URL+"classController/getClassKVList"
/**
 * 添加班级
 * @type {string}
 */
var ADDCLASS_URL = URL+"classController/classesAdd";
/**
 * 编辑班级
 * @type {string}
 */
var UPDATECLASS_URL = URL+"classController/classesUplate";
/**
 * 删除班级
 * @type {string}
 */
var DELETECLASS_URL = URL+"classController/classesDelete";
/**
 * 获取题目类别列表
 * @type {string}
 */
var GETTITLECLASS_URL = URL+"titleClassController/getList";
/**
 * 添加题目类别
 * @type {string}
 */
var ADDTITLECLASS_URL = URL+"titleClassController/add";
/**
 * 编辑题目类别
 * @type {string}
 */
var UPDATETITLECLASS_URL = URL+"titleClassController/update";
/**
 * 删除题目类别
 * @type {string}
 */
var DELETETITLECLASS_URL = URL+"titleClassController/delete";
/**
 * 获取类型键值对
 * @type {string}
 */
var GETTITLECLASSKV_URL = URL+"titleClassController/getKVList";
/**
 * 修改头像
 * @type {string}
 */
var UPDATEPICURL = URL+"userController/updatePic";
/**
 * 修改用户信息
 * @type {string}
 */
var UPDATEUSERINFO_URL = URL+"userController/updateUserInfo";
/**
 * 修改密码
 * @type {string}
 */
var UPDATEPASSWORD_URL = URL+"userController/updatePassword";
/**
 * 获取个人信息
 * @type {string}
 */
var GETUSERINFO_URL = URL+"userController/getUserInfo";

/**
 * 获取题目列表
 * @type {string}
 */
var GETTITLE_URL = URL+"titleController/getList";
/**
 * 获取键值对
 * @type {string}
 */
var GETTITLEKV_URL = URL+"titleController/getKvList";
/**
 * 添加题目
 * @type {string}
 */
var ADDTITLE_URL = URL+"titleController/add";
/**
 * 编辑题目
 * @type {string}
 */
var UPDATETITLE_URL = URL+"titleController/update";
/**
 * 删除题目
 * @type {string}
 */
var DELETETITLE_URL = URL+"titleController/delete";

/**
 * 获取数量
 * @type {string}
 */
var GETCOUNTMAP_URL = URL+"titleController/getCountMap";

/**
 * 获取学生列表
 * @type {string}
 */
var OBTIAN_STUDENTLIST_URL = URL+"studentController/studentSelectList";
/**
 * 删除学生信息
 * @type {string}
 */
var DELETE_STUDENT_URL = URL+"studentController/studentDelete";
/**
 * 修改学生信息
 * @type {string}
 */
var UPDATE_STUDENT_URL = URL+"studentController/studentUpdate";
/**
 * 添加学生信息
 * @type {string}
 */
var ADD_STUDENT_URL = URL+"studentController/studentAdd";

/**
 * 学生excel导入
 * @type {string}
 */
var STUDENT_EXCEL_IMPORT_URL = URL+"studentController/excelImport";

/**
 * 给一个班的学生随机分配题目
 * @type {string}
 */
var ASSIGEN_SUBJECT_ALL_URL = URL+"studentController/assignedSubjectAll";

/**
 * 给单个学生随机分配题目
 * @type {string}
 */
var ASSIGEN_SUBJECT_ONE_URL = URL+"studentController/assignedSubjectOne";
/**
 * 给学生指定一个题目
 * @type {string}
 */
var ASSIGEN_SUBJECT_ONE_SPECIFIED_URL = URL+"studentController/assignedSubjectOneSpecified";
/**
 * 获取可分配题目
 * @type {string}
 */
var GET_SELECTED_TITLE_URL = URL+"studentController/getUnSelectTitle";

/**
 * 学生列表中获取班级信息
 * @type {string}
 */
var GETCLASSSELECT_URL =URL+"classController/getClassKVList"
/**
 * 获取申请换题学生列表
 * @type {string}
 */
var GET_CHANGE_STUDENT =URL+"studentController/getChangeStudentList"

/**
 * 批量更改状态
 * @type {string}
 */
var CHANGE_STATE_URL =URL+"studentController/changeStateBatch"
/**
 * 批量换题
 * @type {string}
 */
var CHANGE_TITLE_URL =URL+"studentController/changeTitleBatch"

/**
 * 导出全班学生开题报告
 * @type {string}
 */
var EXPORT_WORDLIST_URL = URL+"studentController/exportWordBatch"
/**
 * 导出全班学生选题情况
 * @type {string}
 */
var EXPORT_EXCEL_URL = URL+"studentController/exportExcel"
/**
 * 题目类别导入excel
 * @type {string}
 */
var ADMIN_EXCEL_IMPORT_URL = URL+"adminController/excelImport"

