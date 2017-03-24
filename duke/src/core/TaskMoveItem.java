package core;

public class TaskMoveItem extends TaskChain {

	public TaskMoveItem(Item i, Coords3D dropLocation) {
		super(TaskType.MOVING);

		TaskMove t1 = new TaskMove(i.getCoords3D());

		TaskActionPickupItem p1 = new TaskActionPickupItem(i);

		TaskMove t2 = new TaskMove(dropLocation);

		TaskActionDropItem d1 = new TaskActionDropItem(i);

		queueTask(t1);
		queueTask(p1);
		queueTask(t2);
		queueTask(d1);
	}

	@Override
	public void onPickUp(Unit u) {

	}

}
