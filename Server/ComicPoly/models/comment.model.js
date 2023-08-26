var db = require('./db');
const { userModel } = require('./user.model');
const { comicModel } = require('./comic.model')

const commentSchema = new db.mongoose.Schema(
    {
        content: { type: String, require: true },
        time: { type: String, require: true },
        id_comic: { type: db.mongoose.Schema.Types.ObjectId, ref: 'comicModel' },
        id_user: { type: db.mongoose.Schema.Types.ObjectId, ref: 'userModel'}
    },
    {
        collection: 'comment'
    }
);
let commentModel = db.mongoose.model('commentModel', commentSchema);

module.exports = {commentModel};