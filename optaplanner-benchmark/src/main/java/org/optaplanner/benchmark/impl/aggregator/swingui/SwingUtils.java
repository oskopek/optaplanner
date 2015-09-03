/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.benchmark.impl.aggregator.swingui;

import java.util.Enumeration;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicButtonUI;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO move to optaplanner-swingwb, the Swing version of optaplanner-wb (which doesn't exist yet either)
public class SwingUtils {

    private static final Logger logger = LoggerFactory.getLogger(SwingUtils.class);

    public static void fixateLookAndFeel() {
        // increaseDefaultFont(1.5F);
        String lookAndFeelName = "Metal"; // "Nimbus" is nicer but incompatible
        Exception lookAndFeelException;
        String os = SystemUtils.OS_NAME.toLowerCase();
        logger.debug("{}, {}, {}", SystemUtils.OS_ARCH, SystemUtils.OS_NAME, SystemUtils.OS_VERSION);
        try {
            if (os.contains("linux")) {
                lookAndFeelName = "GTK+";
            } else if (os.contains("macintosh")) {
                lookAndFeelName = "Macintosh";
            } else if (os.contains("windows xp")) {
                lookAndFeelName = "Windows XP";
            } else if (os.contains("windows vista")) {
                lookAndFeelName = "Windows Vista";
            } else if (os.contains("windows")) {
                lookAndFeelName = "Windows";
            }
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (lookAndFeelName.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
            lookAndFeelException = null;
        } catch (UnsupportedLookAndFeelException e) {
            lookAndFeelException = e;
        } catch (ClassNotFoundException e) {
            lookAndFeelException = e;
        } catch (InstantiationException e) {
            lookAndFeelException = e;
        } catch (IllegalAccessException e) {
            lookAndFeelException = e;
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            lookAndFeelException = e;
        } catch (InstantiationException e) {
            lookAndFeelException = e;
        } catch (IllegalAccessException e) {
            lookAndFeelException = e;
        } catch (UnsupportedLookAndFeelException e) {
            lookAndFeelException = e;
        }
        logger.warn("Could not switch to lookAndFeel (" + lookAndFeelName + "). Layout might be incorrect.",
                lookAndFeelException);
    }

    public static void increaseDefaultFont(float multiplier) {
        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof FontUIResource) {
                FontUIResource fontUIResource = (FontUIResource) value;
                UIManager.put(key, fontUIResource.deriveFont(fontUIResource.getSize() * multiplier));
            }
        }
    }

    public static class ColorButtonUI extends BasicButtonUI
    {

        private Color background;

        public ColorButtonUI(Color background) {
            this.background = background;
        }

        public void paint ( Graphics g, JComponent c )
        {
            JButton myButton = ( JButton ) c;
            ButtonModel buttonModel = myButton.getModel ();

            if ( buttonModel.isPressed () || buttonModel.isSelected () )
            {
                g.setColor ( Color.GRAY );
            }
            else
            {
                g.setColor ( background );
            }
            g.fillRect ( 0, 0, c.getWidth (), c.getHeight () );

            super.paint ( g, c );
        }
    }

    private SwingUtils() {
    }

}
