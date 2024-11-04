package service;

import model.Progress;

public interface IProgressService extends Service<Progress>{

    void viewUserProgress(String userId);

    void updateUserProgress(String userId);
}
