
package service;

import model.Admin;

public interface IAdminService extends Service<Admin>{
    
    public void add(Admin e);

    public void update(Admin id);

    public void delete(Admin id);
}
