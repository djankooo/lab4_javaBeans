import javax.imageio.ImageIO;
import java.awt.*;
import java.beans.*;
import java.io.IOException;

public class AlarmBeanBeanInfo extends SimpleBeanInfo {

    private final static Class beanClass = AlarmBean.class;
    private final static Class customizerClass = AlarmBeanCustomizer.class;
    Image im = null;

    public Image getIcon(int iconType) {
        String name = "";
        if (iconType == BeanInfo.ICON_COLOR_16x16)
            name = "COLOR_16x16";
        else if (iconType == BeanInfo.ICON_COLOR_32x32)
            name = "COLOR_32x32";
        else if (iconType == BeanInfo.ICON_MONO_16x16)
            name = "MONO_16x16";
        else if (iconType == BeanInfo.ICON_MONO_32x32)
            name = "MONO_32x32";
        else
            return null;
        Image im = null;
        try {
            im = ImageIO.read(AlarmBeanBeanInfo.class.getClassLoader().getResourceAsStream("ChartBean_" + name + ".gif"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return im;
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor backgroundColorDescriptor = new PropertyDescriptor("backgroundColor", AlarmBean.class);
            PropertyDescriptor timeOfSnoozeDescriptor = new PropertyDescriptor("timeOfSnooze", AlarmBean.class);

            return new PropertyDescriptor[] {
                    backgroundColorDescriptor,
                    timeOfSnoozeDescriptor
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BeanDescriptor getBeanDescriptor() {
        return new BeanDescriptor( beanClass, customizerClass);
    }


}
