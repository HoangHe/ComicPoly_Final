var md = require('../../models/comic.model');

exports.list = async (req, res, next)=>{
   try {
      const comic = await md.comicModel.find();
      if (!comic) {
         return res.status(404).json( {status: 0, msg: 'Không tìm thấy truyện'});
      }
      res.json(comic);
   } catch (error) {
      res.json( {status: 0, msg: error.message});
   }
 }
 

exports.detail = async (req, res, next)=>{
   try {
      const comic = await md.comicModel.findById(req.body.id);
      if (!comic) {
         return res.json( {status: 0, msg: 'Không tìm thấy truyện'});
      }
      res.json( {status: 1, data: comic, msg: ''});
   } catch (error) {
      res.json( {status: 0, msg: error.message});
   }
  }
 
  
exports.read = async (req, res, next)=>{
    try {
      const comic = await md.comicModel.findById(req.body.id);
      if (!comic) {
        return res.json( {status: 0, msg: 'Không tìm thấy truyện'});
      }
      res.json(comic.content);
  } catch (error) {
      res.json( {status: 0, msg: error.message});
  }
}
  
 