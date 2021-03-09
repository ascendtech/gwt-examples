package us.ascendtech.client.vuetify;

import com.axellience.vuegwt.core.client.component.options.CustomizeOptions;
import com.axellience.vuegwt.core.client.component.options.VueComponentOptions;

public class VuetifyCustomizeOptions implements CustomizeOptions {

	@Override
	public void customizeOptions(VueComponentOptions vueComponentOptions) {
		VuetifyOptions vuetifyOptions = new VuetifyOptions();

		vuetifyOptions.setTheme(new VuetifyTheme());

		VuetifyThemes themes = new VuetifyThemes();
		vuetifyOptions.getTheme().setThemes(themes);

		VuetifyColors colors = new VuetifyColors();
		colors.setPrimary("#FF5722");
		colors.setSecondary("#03A9F4");
		colors.setAccent("#00B0FF");

		themes.setLight(colors);

		VuetifyIcons icons = new VuetifyIcons();
		icons.setIconfont("mdi");

		vuetifyOptions.setIcons(icons);

		vueComponentOptions.set("vuetify", new Vuetify(vuetifyOptions));
	}

}
