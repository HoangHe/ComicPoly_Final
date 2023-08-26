var myMD = require('../../models/comment.model');


exports.list = async (req, res, next) =>{
    try {
        const comments = await myMD.commentModel.find( {id_comic : req.query.id_comic}).populate('id_user').populate('id_comic');
        if(!comments){
       
        return res.status(404).json({ message: 'Không tìm thấy truyện' });
        }
        res.status(200).json(comments);
      } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
      }
}


exports.send = async (req, res, next)=>{
    try {
        const now = new Date();
        const formattedTime = `${now.getFullYear()}-${(now.getMonth() + 1).toString().padStart(2, '0')}-${now.getDate().toString().padStart(2, '0')} ${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}:${now.getSeconds().toString().padStart(2, '0')}`;
        console.log(formattedTime);
        
        if(!req.body.id_comic || !req.body.id_user  || !req.body.content ){
            return res.status(404).json({message : 'Vui lòng nhập đầy đủ thông tin.'});
        }else{
              const newComment = {
                content: req.body.content,
                time: formattedTime,
                id_comic: req.body.id_comic,
                id_user: req.body.id_user
              };

              const comment = new myMD.commentModel(newComment);
              let new_coment = await comment.save();

              console.log(new_coment);
              return res.json(new_coment);
        }
      } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
      }
}

  