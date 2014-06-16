package DataBase_Layer;

import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.FourmUser.IUser;

public interface IDataMapper {
	
    public void addUser(IUser user);
    public IUser getUser(String user_name);
    public void addSubForum(ISubForum subforum);
    public ISubForum getSubForum(String theme);
    public void addPost(IPost post);
    //public IPost getPost(int post_id);
}
