/**
 * Created by gaowenfeng on 2017/6/12.
 */

$('#username').val(getCookie('username'));
$('#password').val(getCookie('password'));
if(getCookie('username')!=""&&getCookie('username')!=null){
    document.getElementsByName('remember')[0].checked="checked";
}
function sbform(){
    if(document.getElementsByName('remember')[0].checked){
        setCookie('username',$('#username').val(),10);
        setCookie('password',$('#password').val(),10);
    }else{
        setCookie('username',$('#username').val(),-1);
        setCookie('password',$('#password').val(),-1);
    }
    $('#form').submit();
}