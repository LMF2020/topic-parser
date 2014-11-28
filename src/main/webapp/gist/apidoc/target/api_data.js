define({ api: [
  {
    "type": "get",
    "url": "/service/topic/getSheetResult",
    "title": "查询试卷结果",
    "name": "GetSheetResult",
    "group": "Basic_Services",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "stuId",
            "description": "<p>学生编号.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "docId",
            "description": "<p>文档编号.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "state",
            "description": "<p>试卷状态.</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "startDate",
            "description": "<p>查询起始日期(如：<code>2014-11-25 00:00:00</code>).</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "endDate",
            "description": "<p>查询终止日期(如：<code>2014-11-28 23:59:59</code>).</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "obj",
            "description": "<p>[对象数组(对象包含所有字段)].</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>[0,程序异常][1,查询成功][2,查询为空数组].</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>查询试卷答题结果成功.</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "正常返回:",
          "content": "HTTP/1.1 200 OK\n{\n\t \"obj\": [{\n\t     \"id\": 1,\n\t     \"content\": \"{o:1,j:2,w:3,u:9}\",\n\t     \"docId\": 55,\n\t     \"stuId\": 069084043,\n\t     \"commitTime\": \"2014-11-27 23:43:40\",\n\t     \"state\": \"1\"\n\t  }],   //注意这个对象是个数组,因为有可能查询出N条记录\n\t  \"code\": 1,\n\t  \"msg\": \"查询试卷答题结果成功\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "source/doc.js",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "msg",
            "description": "<p>失败消息</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "失败返回:",
          "content": "    HTTP/1.1 200\n    {\n    \t \"objId\":[], //空数组\n      \"code\":0,\n      \"msg\":\"查询结果返回失败\"\n    }",
          "type": "json"
        }
      ]
    },
    "groupTitle": "Basic_Services"
  },
  {
    "type": "put",
    "url": "/service/topic/putSheetResult",
    "title": "保存试卷结果",
    "name": "PutSheetResult",
    "group": "Basic_Services",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>作业内容.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "docId",
            "description": "<p>文档编号.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "stuId",
            "description": "<p>学生编号.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "state",
            "description": "<p>试卷状态(保留一位字符)[1][2][3][4].</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "obj",
            "description": "<p>[该条记录的主键]</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>[0,程序异常][1,新增成功][2,更新成功]</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>新增[更新]试卷答题结果成功.</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "正常返回:",
          "content": "HTTP/1.1 200 OK\n{\n\t \"obj\":  7,\n\t \"code\": 2,  //或1或2\n\t \"msg\": \"更新试卷答题结果成功\" //或更新,或新增\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "source/doc.js",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "msg1",
            "description": "<p>(失败消息)【试卷状态】保留一位有效字符</p> "
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "msg2",
            "description": "<p>(失败消息)【学生ID】和【文档ID】不能为空.</p> "
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "msg3",
            "description": "<p>(失败消息)保存试卷答题结果出错.</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "失败返回:",
          "content": "    HTTP/1.1 200\n    {\n    \t \"obj\":\"\",\n      \"code\":0,\n      \"msg\":\"结果返回失败\" //还有两种校验失败的情况[如上msg]\n    }",
          "type": "json"
        }
      ]
    },
    "groupTitle": "Basic_Services"
  }
] });