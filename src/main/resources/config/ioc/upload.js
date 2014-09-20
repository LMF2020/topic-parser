/**
 * 以下是文件上传的相关配置
 */
var ioc = {
        utils : { //文件工具类
            type : 'com.topic.parserAdapter.core.util.MyFileUtils',
            fields : {
                sc : {app:'$servlet'}   // 将 ServletContext 对象注入 MyFileUtils
            }
        },
        tmpFilePool : { //配置文件池
            type : 'org.nutz.filepool.NutFilePool',
            args : [ {java:'$utils.getUploadPath("/doc")'}, 1000 ]   // 文件上传到应用的/doc目录下
        },
        uploadFileContext : {
            type : 'org.nutz.mvc.upload.UploadingContext',
            singleton : false,
            args : [ { refer : 'tmpFilePool' } ],
            fields : {
                // 是否忽略空文件, 默认为 false
                ignoreNull : true,
                // 单个文件最大尺寸(大约的值，单位为字节，即 1048576 为 1M)
                maxFileSize : 5342880,
                // 正则表达式匹配可以支持的文件名
                nameFilter : '^(.+[.])(doc|docx)$' //目前只支持office Word
            } 
        },
        myUpload : {
            type : 'org.nutz.mvc.upload.UploadAdaptor',
            singleton : false,
            args : [ { refer : 'uploadFileContext' } ] 
        }
};