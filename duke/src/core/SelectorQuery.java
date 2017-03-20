package core;

public class SelectorQuery {

	private String name;
	private String[] options;
	private int selectedOption;

	public SelectorQuery(String name, String[] options) {
		this.name = name;
		this.options = options;
		this.selectedOption = 0;
	}

	public String getName() {
		return name;
	}

	public String getSelectedOption() {
		return options[selectedOption];
	}

	public String[] getOptions() {
		return options;
	}

	public void nextOption() {
		if (selectedOption < options.length - 1)
			selectedOption++;
		else
			selectedOption = 0;
	}

	public void previousOption() {
		if (selectedOption > 0)
			selectedOption--;
		else
			selectedOption = options.length - 1;

	}

}
