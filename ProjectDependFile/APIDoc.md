
 <h1 class="curproject-name"> 易达商城 </h1> 
 易达商城接口文档


# User

## 用户登录
<a id=用户登录> </a>
### 基本信息

**Path：** /user/login

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Body**

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> username</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">用户名</span></td><td key=5></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> password</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">密码</span></td><td key=5></td></tr>
               </tbody>
              </table>
            
### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody">
               </tbody>
              </table>
            
## 获取用户详情信息
<a id=获取用户详情信息> </a>
### 基本信息

**Path：** /user/get_information

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |

## 登录状态的重置密码
<a id=登录状态的重置密码> </a>
### 基本信息

**Path：** /user/reset_password

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| passwordOld | 是  |   |  老密码 |
| passwordNew | 是  |   |  新密码 |

## 用户注册
<a id=用户注册> </a>
### 基本信息

**Path：** /user/register

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Body**

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> username</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">用户名</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>zhangsan1</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> password</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">密码</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>zhangsan1</span></p></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> email</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">邮箱</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>zhangsan@qq.com</span></p></td></tr>
               </tbody>
              </table>
            
## 用户注销
<a id=用户注销> </a>
### 基本信息

**Path：** /user/logout

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |

## 更新用户个人信息
<a id=更新用户个人信息> </a>
### 基本信息

**Path：** /user/update_information

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Body**

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> email</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">用户游戏啊行</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>admin123@qq.com</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> phone</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">用户密码</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>13596347415</span></p></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> question</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">密码提示问题</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>问题</span></p></td></tr><tr key=0-3><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> answer</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">密码提示答案</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>答案</span></p></td></tr>
               </tbody>
              </table>
            
## 忘记密码:重设密码
<a id=忘记密码:重设密码> </a>
### 基本信息

**Path：** /user/forget_rest_password

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| username | 是  |   |  用户名 |
| passwordNew | 是  |   |  新密码 |
| forgetToken | 是  |   |  忘记密码,回答正确的密码提示问题之后返回的唯一Token |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody">
               </tbody>
              </table>
            
## 获取用户详情
<a id=获取用户详情> </a>
### 基本信息

**Path：** /user/get_user_info

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |

## 提交密码提示问题的答案
<a id=提交密码提示问题的答案> </a>
### 基本信息

**Path：** /user/forget_check_answer

**Method：** POST

**接口描述：**
<p>问题和答案校验成功之后会返回一个唯一Token,修改密码的时候需要用到这个唯一Token</p>


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| username | 是  |  admin |  用户名 |
| question | 是  |  问题 |  密码提示的问题 |
| answer | 是  |  答案 |  密码提示问题的答案 |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> status</span></td><td key=1><span>number</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap"></span></td><td key=5></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> msg</span></td><td key=1><span>null</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap"></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> data</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">唯一Koken</span></td><td key=5></td></tr><tr key=0-3><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> success</span></td><td key=1><span>boolean</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap"></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
## 获取用户密码提示问题
<a id=获取用户密码提示问题> </a>
### 基本信息

**Path：** /user/forget_get_question

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| username | 是  |  wanglong |  用户名 |

## 参数校验
<a id=参数校验> </a>
### 基本信息

**Path：** /user/check_valid

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| str | 是  |  admin111@happymmall.com |  用户名或者邮箱号 |
| type | 是  |  email |  参数类型:用户名或邮箱号 |
**Body**

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody">
               </tbody>
              </table>
            
## 后台管理员用户登录
<a id=后台管理员用户登录> </a>
### 基本信息

**Path：** /manage/user/login

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |
**Body**

| 参数名称  | 参数类型  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| username | text  |  是 |  管理员用户名  |   |
| password | text  |  是 |  管理员密码  |   |



# Shipping

## 删除收货地址
<a id=删除收货地址> </a>
### 基本信息

**Path：** /shipping/del

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| shippingId | 是  |  37 |  收获地址ID |

## 所有收货地址
<a id=所有收货地址> </a>
### 基本信息

**Path：** /shipping/list

**Method：** GET

**接口描述：**
undefined

### 请求参数

## 更新收获地址
<a id=更新收获地址> </a>
### 基本信息

**Path：** /shipping/update

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| id | 是  |   |  收货地址ID |
| receiverName | 是  |   |  收货人名字 |

## 查看收货地址详情
<a id=查看收货地址详情> </a>
### 基本信息

**Path：** /shipping/select

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| shippingId | 是  |   |  收货地址ID |

## 添加收货地址
<a id=添加收货地址> </a>
### 基本信息

**Path：** /shipping/add

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| userId | 是  |   |  用户id |
| receiverName | 是  |   |  收货人 |
| receiverPhone | 是  |   |  收货人电话 |
| receiverMobile | 是  |   |  收货人手机 |
| receiverProvince | 是  |   |  收货人省份 |
| receiverCity | 是  |   |  收货人城市 |
| receiverAddress | 是  |   |  收货人详细地址 |
| receiverZip | 是  |   |  收货人邮编 |
**Body**

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> userId</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">用户ID</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>1</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> receiverName</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">收货人名字</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>张飞</span></p></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> receiverPhone</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">收货人电话</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>03715639129</span></p></td></tr><tr key=0-3><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> receiverMobile</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">收货人手机</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>15939946628</span></p></td></tr><tr key=0-4><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> receiverProvince</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">收货人省份</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>河南</span></p></td></tr><tr key=0-5><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> receiverCity</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">收货人城市</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>郑州</span></p></td></tr><tr key=0-6><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> receiverAddress</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">收货人详细地址</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>管城区</span></p></td></tr><tr key=0-7><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> receiverZip</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">收货人邮编</span></td><td key=5><p key=5><span style="font-weight: '700'">undefined: </span><span>566660</span></p></td></tr>
               </tbody>
              </table>
            
# Product

## 商品列表/搜索/排序
<a id=商品列表/搜索/排序> </a>
### 基本信息

**Path：** /product/list

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| keyword | 否  |  i |   |
| orderBy | 否  |  price_desc |   |

## 商品详情
<a id=商品详情> </a>
### 基本信息

**Path：** /product/detail

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| productId | 是  |   |  27 |

## 后台列出所有商品
<a id=后台列出所有商品> </a>
### 基本信息

**Path：** /manage/product/list

**Method：** GET

**接口描述：**


### 请求参数

## 后台产品搜索
<a id=后台产品搜索> </a>
### 基本信息

**Path：** /manage/product/search

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| productName | 是  |  美的 |  产品名 |
| productId | 否  |  27 |  产品ID |

## 后台查看商品详情
<a id=后台查看商品详情> </a>
### 基本信息

**Path：** /manage/product/detail

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| productId | 是  |  30 |  商品ID |

## 后台更改产品状态(上架,下架)
<a id=后台更改产品状态(上架,下架)> </a>
### 基本信息

**Path：** /manage/product/set_sale_status

**Method：** GET

**接口描述：**
<p>1-在售 2-下架 3-删除</p>


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| productId | 是  |   |   |
| status | 是  |   |   |

## 后台富文本商品图片上传
<a id=后台富文本商品图片上传> </a>
### 基本信息

**Path：** /manage/product/richtext_img_upload

**Method：** POST

**接口描述：**
<p>该接口在Yapi环境下无法使用,请使用Talend API Tester 工具进行测试<br>
错误原因,Yapi环境下无法生成<strong>Boundary</strong></p>


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  multipart/form-data | 是  |   |   |
**Body**

| 参数名称  | 参数类型  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| upload_file | file  |  是 |    |   |



## 后台上传商品图片
<a id=后台上传商品图片> </a>
### 基本信息

**Path：** /manage/product/upload

**Method：** GET

**接口描述：**
<p>该接口在Yapi环境下无法使用,请使用Talend API Tester 工具进行测试<br>
错误原因,Yapi环境下无法生成<strong>Boundary</strong></p>


### 请求参数

## 后台添加/修改商品
<a id=后台添加/修改商品> </a>
### 基本信息

**Path：** /manage/product/save

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| categoryId | 是  |   |  商品分类ID |
| name | 是  |   |  商品名 |
| subtitle | 是  |   |  商品名简写 |
| subImages | 是  |   |  商品图 |
| detail | 是  |   |  商品详情 |
| price | 是  |   |  商品价格 |
| stock | 是  |   |  商品库存 |
| status | 是  |   |  商品状态 |
| id | 是  |   |  商品ID |

# Category

## 更新产品分类的名字
<a id=更新产品分类的名字> </a>
### 基本信息

**Path：** /manage/category/set_category_name

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| parentId | 是  |   |  父节点ID |
| categoryName | 是  |   |  商品分类名字 |

## 添加分类
<a id=添加分类> </a>
### 基本信息

**Path：** /manage/category/add_category

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| categoryName | 是  |   |  商品分类名 |

## 获取商品分类
<a id=获取商品分类> </a>
### 基本信息

**Path：** /manage/category/get_category

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| categoryId | 否  |   |  商品分类ID |

## 递归获取商品分类
<a id=递归获取商品分类> </a>
### 基本信息

**Path：** /manage/category/get_deep_category

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| categoryId | 否  |   |  商品分类ID |

# Cart

## 取消选择购物车中的某个产品
<a id=取消选择购物车中的某个产品> </a>
### 基本信息

**Path：** /cart/un_select

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| productId | 是  |   |  商品ID |

## 更新购物车中某个产品的数量
<a id=更新购物车中某个产品的数量> </a>
### 基本信息

**Path：** /cart/update

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| count | 是  |   |  产品数量 |
| productId | 是  |   |  产品ID |

## 购物车中的物品全选
<a id=购物车中的物品全选> </a>
### 基本信息

**Path：** /cart/select_all

**Method：** GET

**接口描述：**
undefined

### 请求参数

## 购物车中的物品全反选
<a id=购物车中的物品全反选> </a>
### 基本信息

**Path：** /cart/un_select_all

**Method：** GET

**接口描述：**


### 请求参数

## 删除购物车中的商品
<a id=删除购物车中的商品> </a>
### 基本信息

**Path：** /cart/delete_product

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| productIds | 是  |   |  购物车中的商品ID可以同时删除多个,多个商品ID需要用逗号分隔 |

## 查询购物车中所有的商品
<a id=查询购物车中所有的商品> </a>
### 基本信息

**Path：** /cart/list

**Method：** GET

**接口描述：**
undefined

### 请求参数

## 获取购物车中产品的数量
<a id=获取购物车中产品的数量> </a>
### 基本信息

**Path：** /cart/get_cart_product_count

**Method：** GET

**接口描述：**
undefined

### 请求参数

## 单独选择购物车中的某个产品
<a id=单独选择购物车中的某个产品> </a>
### 基本信息

**Path：** /cart/select

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| productId | 是  |   |  商品ID |

## 新建购物车
<a id=新建购物车> </a>
### 基本信息

**Path：** /cart/add

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| count | 是  |   |  加入购物车的产品数量 |
| productId | 是  |   |  产品ID |

# Order

## 后台商品发货
<a id=后台商品发货> </a>
### 基本信息

**Path：** /manage/order/send_goods

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| orderNo | 是  |   |  订单号码 |

## 后台搜索订单
<a id=后台搜索订单> </a>
### 基本信息

**Path：** /manage/order/search

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| orderNo | 是  |   |  订单号 |

## 后台订单列表
<a id=后台订单列表> </a>
### 基本信息

**Path：** /manage/order/orderlist

**Method：** GET

**接口描述：**
undefined

### 请求参数

## 后台订单详情
<a id=后台订单详情> </a>
### 基本信息

**Path：** /manage/order/detail

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| orderNo | 是  |   |  订单号码 |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody">
               </tbody>
              </table>
            
## 获取购物车中已经选中的商品详情
<a id=获取购物车中已经选中的商品详情> </a>
### 基本信息

**Path：** /order/get_order_cart_product

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| orderNo | 否  |   |  订单号 |

## 订单详情的查看
<a id=订单详情的查看> </a>
### 基本信息

**Path：** /order/detail

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| orderNo | 是  |   |  订单号 |

## 订单列表
<a id=订单列表> </a>
### 基本信息

**Path：** /order/list

**Method：** GET

**接口描述：**
undefined

### 请求参数

## 创建订单
<a id=创建订单> </a>
### 基本信息

**Path：** /order/create

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| shippingId | 是  |   |  用户收获地址 |

## 支付宝回调
<a id=支付宝回调> </a>
### 基本信息

**Path：** /order/alipay_callback

**Method：** GET

**接口描述：**


### 请求参数

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody">
               </tbody>
              </table>
            
## 取消订单功能
<a id=取消订单功能> </a>
### 基本信息

**Path：** /order/cancle

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| orderNo | 是  |   |  订单编号(可以从订单列表中获取) |

## 查询订单的支付状态
<a id=查询订单的支付状态> </a>
### 基本信息

**Path：** /order/query_order_pay_status

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| orderNo | 是  |   |  订单号 |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody">
               </tbody>
              </table>
            
## 订单支付
<a id=订单支付> </a>
### 基本信息

**Path：** /order/pay

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| orderNo | 是  |   |  订单号 |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> status</span></td><td key=1><span>number</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap"></span></td><td key=5></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> msg</span></td><td key=1><span>null</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap"></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> data</span></td><td key=1><span>object</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap"></span></td><td key=5></td></tr><tr key=0-2-0><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> orderNo</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">订单号</span></td><td key=5></td></tr><tr key=0-2-1><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> qrUrl</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap">订单生成的二维码链接(用户扫码)</span></td><td key=5></td></tr><tr key=0-3><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> success</span></td><td key=1><span>boolean</span></td><td key=2>必须</td><td key=3></td><td key=4><span style="white-space: pre-wrap"></span></td><td key=5></td></tr>
               </tbody>
              </table>
            