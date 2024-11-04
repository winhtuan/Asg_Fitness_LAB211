
package service;

import model.Workout;


public interface IWorkoutService extends Service<Workout>{
   public void createWorkOut(Workout e); 
   public void update(Workout e);
   public void delete(String e );
}
