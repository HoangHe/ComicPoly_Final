var express = require('express');
var router = express.Router();
var commentController = require('../controllers/comment.controller');



router.get('/', commentController.list);
router.get('/list', commentController.list);
router.post('/add', commentController.add);
router.put('/update', commentController.update);
router.delete('/delete', commentController.delete);


module.exports = router;
