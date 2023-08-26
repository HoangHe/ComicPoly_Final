var express = require('express');
var router = express.Router();
var userApi = require('../controllers/api/user.api');
var comicApi = require('../controllers/api/comic.api');
var commentApi = require('../controllers/api/comment.api');

router.post('/users/login', userApi.login); 
router.post('/users/register', userApi.register); 
router.post('/users/logout', userApi.logout);

router.get('/users/list', userApi.list);
router.put('/users/update', userApi.update);
router.delete('/users/delete', userApi.delete);


router.get('/comic/list', comicApi.list);
router.get('/comic/detail', comicApi.detail);
router.get('/comic/read', comicApi.read);

router.get('/comment/list', commentApi.list);
router.post('/comment/send', commentApi.send);

module.exports = router;

