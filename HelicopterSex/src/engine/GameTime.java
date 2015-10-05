package engine;

public class GameTime
{
	private long targetRate;
	private double targetPeriod;
	
	private long startTime;
	private long elapsedTime = 0;
	private float dt_ms = 0;
	private float dt_s;
	
	private float timeScale = 1f;
		
	public
	GameTime(long targetRate)
	{
		this.targetRate = targetRate;
		if (this.targetRate <= 0)
		{
			this.targetRate = 60;
		}
		
		this.targetPeriod = 1.0 / this.targetRate;
		
	}
	
	public void
	startCounting()
	{
		startTime = System.nanoTime();
		elapsedTime = 0;
	}
	
	public void 
	beginCycle()
	{
		long currentTime = System.nanoTime();
		if(startTime != 0)
			elapsedTime = currentTime - startTime;
		startTime = currentTime;
		
		dt_ms = elapsedTime / 1000000.0f;
		dt_s = dt_ms / 1000.0f;
		
	}
	
	public long 
	calculateSleepTime()
	{
		long sleepTime = ((long)(targetPeriod * 1000000000 - (System.nanoTime() - startTime)) / 1000000);
		System.out.println(sleepTime);

		return sleepTime;
	}
	
	@SuppressWarnings("unused")
	private float 
	dt_ms()
	{
		return dt_ms * timeScale;
	}
	
	public float 
	dt_s()
	{
		return dt_s * timeScale;
	}

	public float 
	getTimeScale()
	{
		return timeScale;
	}

	public void 
	setTimeScale(float timeScale)
	{
		this.timeScale = timeScale;
	}
		
}


