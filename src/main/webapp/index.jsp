<%--
  Created by IntelliJ IDEA.
  User: msi-
  Date: 2018/3/3
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ShiyanlouPhoto</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <![endif]-->
  </head>
  <body>
    <div class="container">
      <div class="row">
        <div class="col-xs-12 text-center">
          <h2>我的个人相册</h2>
        </div>
      </div>
      <div class="row">
        <div id="alert1" class="alert alert-success fade in text-center col-xs-2 col-xsl-offset-5 hide">
          <strong>RegisterSuccess</strong>
        </div>
      </div>
      <form id="from" class="form-horizontal" role="form" style="margin-top:73px;">
        <div class="form-group">
          <label for="username" class="col-xs-2 control-label col-sm-offset-3">UserName</label>
          <div class="col-xs-2">
            <input type="text" class="form-control" id="username" rel="tooltip" />
          </div>
        </div>
        <div class="form-group">
          <label for="password" class="col-xs-2 control-label col-sm-offset-3">Password</label>
          <div class="col-xs-2">
            <input type="text" class="form-control" id="password" />
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-5 col-xs-1">
               <button id="login" type="button" class="btn btn-success">login</button>
          </div>
          <div class="col-xs-1">
            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal">register</button>
          </div>
        </div>
      </form>
    </div>
    <!-- 注册对话框 begin -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">UserRegister</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal" role="form">
              <div class="form-group"  >
                <label for="reg_username" class="col-xs-2 control-label" >Username</label>
                <div class="col-xs-4">
                  <input type="text" class="form-control" id="reg_username"/>
                </div>
              </div>
              <div class="form-group">
                <label for="reg_password" class="col-xs-2 control-label">Password</label>
                <div class="col-xs-4">
                  <input type="password" class="form-control" id="reg_password"/>
                </div>
              </div>
              <div class="form-group">
                <label for="reg_repassword" class="col-xs-2 control-label">Reinput</label>
                <div class="col-xs-4">
                  <input type="password" class="form-control" id="reg_repassword"/>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            <button type="button" class="btn btn-primary" id="register">Register</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 注册对话框 end -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            //点击登录
            $('#login').click(function() {
                //提交登录表单
                $.post('<c:url value="/doLogin.html" />',
                    {
                        userName: $('#username').val(),
                        password: $('#password').val()
                    },
                    function(data, status) {
                        if (data == '1') {
                            createPopOver('#username', 'right', 'Username can not be empty ', 'show');
                        } else if (data == '2') {
                            createPopOver('#password', 'right', ' password can not be empty', 'show');
                        } else if (data == '3') {
                            createPopOver('#username', 'right', ' Username does not exist', 'show');
                        } else if (data == '4') {
                            createPopOver('#password', 'right', ' wrong password', 'show');
                        } else if (data == 5) {
                            location.href = '${pageContext.request.contextPath}' + '/home.html';
                        }
                    });
            });
      //点击注册按钮
      $('#register').click(function() {
          //提交注册表单
          $.post('${pageContext.request.contextPath}' + '/doRegister.html',
              {
                  userName: $('#reg_username').val(),
                  password: $('#reg_password').val(),
                  repassword: $('#reg_repassword').val()
              },
              function(data, status) {
                  if (data == '1') {
                      createPopOver('#reg_username', 'right', 'not null', 'show');
                  } else if (data == '2') {
                      createPopOver('#reg_password', 'right', 'not null', 'show');
                  } else if (data == '3') {
                      createPopOver('#reg_repassword', 'right', 'not null', 'show');
                  } else if (data == '4') {
                      createPopOver('#reg_repassword', 'right', 'Passwords do not match', 'show');
                  } else if (data == '5') {
                      createPopOver('#reg_username', 'right', 'Username already exists', 'show');
                  } else if (data == '6') {
                      $('#reg_username').val('');
                      $('#reg_password').val('');
                      $('#reg_repassword').val('');
                      $('#myModal').modal('hide');
                      $('#alert1').removeClass('hide');
                      $('#form').css('margin-top', '0px');
                  }
              });
      });
            //显示弹出框
            function createPopOver(id, placement, content, action) {
                $(id).popover({
                    placement: placement,
                    content: content
                });
                $(id).popover(action);
            }

            //点击输入框时销毁弹出框
            $('#username').click(function() {
                $('#username').popover('destroy');
            });

            $('#password').click(function() {
                $('#password').popover('destroy');
            });

            $('#reg_username').click(function() {
                $('#reg_username').popover('destroy');
            });

            $('#reg_password').click(function() {
                $('#reg_password').popover('destroy');
            });

            $('#reg_repassword').click(function() {
                $('#reg_repassword').popover('destroy');
            });
        });
    </script>
  </body>
</html>
