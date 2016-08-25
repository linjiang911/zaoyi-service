
   function funcname(callback){
        callback(1); 
    }

/* 
* 正则验证 
* @param s 验证字符串 
* @param type 验证类型 money,china,mobile等  
* @return 
*/  
function verify(s, type,callback) {  
    var objbool = false;  
    var objexp = "";  
    switch (type) {  
        case 'money': //金额格式,格式定义为带小数的正数，小数点后最多三位  
            objexp = "^[0-9]+[\.][0-9]{0,3}$";  
            break;  
        case 'numletter_': //英文字母和数字和下划线组成     
            objexp = "^[0-9a-zA-Z\_]+$";  
            break;  
        case 'numletter': //英文字母和数字组成  
            objexp = "^[0-9a-zA-Z]+$";  
            break;  
        case 'numletterchina': //汉字、字母、数字组成   
            objexp = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";  
            break;  
        case 'email': //邮件地址格式   
            objexp = "^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$";  
            break;    
        case 'tel': //固话格式   
            objexp = /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;  
            break;  
        case 'mobile': //手机号码   
            objexp = "^(13[0-9]|15[0-9]|18[0-9])([0-9]{8})$";  
            break;  
        case 'decimal': //浮点数   
            objexp = "^[0-9]+([.][0-9]+)?$";  
            break;  
        case 'url': //网址   
            objexp = "(http://|https://){0,1}[\w\/\.\?\&\=]+";  
            break;  
        case 'date': //日期 YYYY-MM-DD格式  
            objexp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;  
            break;  
        case 'int': //整数   
            objexp = "^[0-9]*[1-9][0-9]*$";  
            break;  
        case 'int+': //正整数包含0  
            objexp = "^\\d+$";  
            break;  
        case 'int-': //负整数包含0  
            objexp = "^((-\\d+)|(0+))$";  
            break;  
        case 'china': //中文   
            objexp = /^[\u0391-\uFFE5]+$/;  
            break;  
    }
    var re = new RegExp(objexp);  
    var falge=false;
    if (re.test(s)) {  
    	falge=true;
    }  
    else {
    	falge=false;
    }
    callback(falge);
};

/* 
* 正则验证 
* @param s 验证字符串 
* @param type 验证类型 money,china,mobile等  
* @return 
*/  
function verifys(s, type) {  
    var objbool = false;  
    var objexp = "";  
    switch (type) {  
        case 'money': //金额格式,格式定义为带小数的正数，小数点后最多三位  
            objexp = "^[0-9]+[\.][0-9]{0,3}$";  
            break;  
        case 'numletter_': //英文字母和数字和下划线组成     
            objexp = "^[0-9a-zA-Z\_]+$";  
            break;  
        case 'numletter': //英文字母和数字组成  
            objexp = "^[0-9a-zA-Z]+$";  
            break;  
        case 'notNull': //不能有空格符号
        	objexp = "^[^\\s]+$";  
        	break;  
        case 'pwd': //密码不能为空10-16位，要包含特殊符号  
        	objexp = "^(?=[`~!@#\$%\^&*\(\)\-_=\+\\\|\[\]\{\}:;\"\',.<>\/\?\d]*[a-zA-Z]+)(?=[a-zA-Z`~!@#\$%\^&*\(\)\-_=\+\\\|\[\]\{\}:;\"\',.<>\/\?]*\d+)[`~!@#\$%\^&*\(\)\-_=\+\\\|\[\]\{\}:;\"\',.<>\/\?\w]{10,16}$";  
        	break;  
        case 'numletterchina': //汉字、字母、数字组成   
            objexp = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";  
            break;  
        case 'email': //邮件地址格式   
            objexp = "^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$";  
            break;    
        case 'tel': //固话格式   
            objexp = /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;  
            break;  
        case 'mobile': //手机号码   
            objexp = "^(13[0-9]|15[0-9]|18[0-9])([0-9]{8})$";  
            break;  
        case 'decimal': //浮点数   
            objexp = "^[0-9]+([.][0-9]+)?$";  
            break;  
        case 'url': //网址   
            objexp = "(http://|https://){0,1}[\w\/\.\?\&\=]+";  
            break;  
        case 'date': //日期 YYYY-MM-DD格式  
            objexp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;  
            break;  
        case 'int': //整数   
            objexp = "^[0-9]*[1-9][0-9]*$";  
            break;  
        case 'int+': //正整数包含0  
            objexp = "^\\d+$";  
            break;  
        case 'int-': //负整数包含0  
            objexp = "^((-\\d+)|(0+))$";  
            break;  
        case 'china': //中文   
            objexp = /^[\u0391-\uFFE5]+$/;  
            break;  
    }
    var re = new RegExp(objexp);  
    if (re.test(s)) {  
    	return true;
    }  
    else {
    	return false;
    }
};