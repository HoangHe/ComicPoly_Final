var express = require('express');
var router = express.Router();
var comicController = require('../controllers/comic.controller');
var  api_auth  = require('../middleware/api.auth');


router.get('/', comicController.list);
router.get('/list', comicController.list);
router.post('/add', comicController.add);
router.put('/update', comicController.update);
router.delete('/delete', comicController.delete);
router.put('/addContent', comicController.addContentToComic);
router.put('/deleteContent', comicController.deleteContent);



module.exports = router;
