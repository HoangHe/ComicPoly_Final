var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var session = require('express-session')

var comicRouter = require('./routes/comic');
var commentRouter = require('./routes/comment');
var usersRouter = require('./routes/users');
var apiRouter   = require('./routes/api');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(session({
  secret:'sdawqewecxcdasdwqewqeqdaxczcxzcxds21231ds3122412ds', // chuỗi kí tự đặc biệt để mã hoá
  resave:true,
  saveUninitialized:true
 }));

app.use('/', comicRouter);
app.use('/comic', comicRouter);
app.use('/users', usersRouter);
app.use('/comment', commentRouter);
app.use('/api',apiRouter);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  //Tuỳ chỉnh lại render cho phù hợp với API
  // VD: link api: GET /api/users

  if (req.originalUrl.indexOf('/api') === 0) {
    //link bắt đầu bằng chữ "/api" là truy cập vào trang API ==> thông báo lỗi kiểu API
    res.json({
      status: 0,
      msg: err.message
    }); 
  }else{
    res.render('error');
  }
});

module.exports = app;
