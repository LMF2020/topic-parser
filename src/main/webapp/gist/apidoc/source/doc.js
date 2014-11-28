/*
 *  题库接口规范 
 */

/**
 * @apiDefineErrorStructure PutNotExceptedError
 *
 * @apiError msg1  (失败消息)【试卷状态】保留一位有效字符
 * @apiError msg2  (失败消息)【学生ID】和【文档ID】不能为空.
 * @apiError msg3  (失败消息)保存试卷答题结果出错.
 *
 * @apiErrorExample 失败返回:
 *     HTTP/1.1 200
 *     {
 *     	 "obj":"",
 *       "code":0,
 *       "msg":"结果返回失败" //还有两种校验失败的情况[如上msg]
 *     }
 */

/**
 * @apiDefineErrorStructure NormalExceptedError
 *
 * @apiError msg  失败消息
 *
 * @apiErrorExample 失败返回:
 *     HTTP/1.1 200
 *     {
 *     	 "objId":[], //空数组
 *       "code":0,
 *       "msg":"查询结果返回失败"
 *     }
 */

/**
 * @api {put} /service/topic/putSheetResult 保存试卷结果
 * @apiName PutSheetResult
 * @apiGroup Basic Services
 *
 * @apiParam {String} content 作业内容.
 * @apiParam {Number} docId 文档编号.
 * @apiParam {Number} stuId 学生编号.
 * @apiParam {Number} state 试卷状态(保留一位字符)[1][2][3][4].
 * 
 * @apiSuccess {String} obj [该条记录的主键]
 * @apiSuccess {String} code  [0,程序异常][1,新增成功][2,更新成功]
 * @apiSuccess {String} msg  新增[更新]试卷答题结果成功.
 *
 * @apiSuccessExample 正常返回:
 * HTTP/1.1 200 OK
 * {
 *	 "obj":  7,
 *	 "code": 2,  //或1或2
 *	 "msg": "更新试卷答题结果成功" //或更新,或新增
 * }
 * 
 * @apiErrorStructure PutNotExceptedError
 */

/**
 * @api {get} /service/topic/getSheetResult 查询试卷结果
 * @apiName GetSheetResult
 * @apiGroup Basic Services
 * 
 * @apiParam {Number} stuId 学生编号.
 * @apiParam {Number} [docId] 文档编号.
 * @apiParam {Number} [state] 试卷状态.
 * @apiParam {String} [startDate] 查询起始日期(如：<code>2014-11-25 00:00:00</code>).
 * @apiParam {String} [endDate] 查询终止日期(如：<code>2014-11-28 23:59:59</code>).
 * 
 * @apiSuccess {String} obj [对象数组(对象包含所有字段)].
 * @apiSuccess {String} code  [0,程序异常][1,查询成功][2,查询为空数组].
 * @apiSuccess {String} msg  查询试卷答题结果成功.
 *
 * @apiSuccessExample 正常返回:
 * HTTP/1.1 200 OK
 * {
 *	 "obj": [{
 *	     "id": 1,
 *	     "content": "{o:1,j:2,w:3,u:9}",
 *	     "docId": 55,
 *	     "stuId": 069084043,
 *	     "commitTime": "2014-11-27 23:43:40",
 *	     "state": "1"
 *	  }],   //注意这个对象是个数组,因为有可能查询出N条记录
 *	  "code": 1,
 *	  "msg": "查询试卷答题结果成功"
 * }
 *
 * @apiErrorStructure NormalExceptedError
 */