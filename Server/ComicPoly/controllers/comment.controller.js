var myMD = require('../models/comment.model');


exports.list = async (req, res, next) =>{
    try {
        const comments = await myMD.commentModel.find().populate('id_user').populate('id_comic');
        res.status(200).json(comments);
      } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
      }
}

exports.add = async (req, res, next)=>{
    try {
        const now = new Date();
        const formattedTime = `${now.getFullYear()}-${(now.getMonth() + 1).toString().padStart(2, '0')}-${now.getDate().toString().padStart(2, '0')} ${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}:${now.getSeconds().toString().padStart(2, '0')}`;
        console.log(formattedTime);
        
        if(!req.body.id_comic || !req.body.id_user  || !req.body.content ){
            res.status(201).json({message : 'Vui lòng nhập đầy đủ thông tin.'});
        }else{
            const newComment = {
                content: req.body.content,
                time: formattedTime,
                id_comic: req.body.id_comic,
                id_user: req.body.id_user
              };
              const createdComment = await myMD.commentModel.create(newComment);
              res.status(201).json(createdComment);
        }
      } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
      }
}

exports.update = async (req, res, next) =>{
    try {
        if(!req.body.content){
            res.status(404).json({ message: 'Nội dung không được để trống.' });
        }else if(!req.body.id_comic || !req.body.id_user){
            res.status(404).json({ message: 'Vui lòng nhập đầy đủ thông tin.' });
        }else{
            const updatedComment = await myMD.commentModel.findByIdAndUpdate(req.body.id, req.body, { new: true }).populate('id_user').populate('id_comic');
            if (!updatedComment) {
                res.status(404).json({ message: 'Comment not found' });
            } else {
                res.json(updatedComment);
            }
        }
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: 'Internal server error' });
    }
}

exports.delete = async (req, res, next) =>{
    try {
        if(req.body.id){
        const deletedComment = await myMD.commentModel.findByIdAndDelete(req.body.id).populate('id_user').populate('id_comic');
            if (!deletedComment) {
            res.status(404).json({ message: 'Comment not found' });
            } else {
            res.json(deletedComment);
            }
        }else{
            res.status(404).json({ message: 'Vui lòng nhập id cần xóa.' });
        }
      } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
      }
}

