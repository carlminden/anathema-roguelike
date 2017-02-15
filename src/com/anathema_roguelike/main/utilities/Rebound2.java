package com.anathema_roguelike.main.utilities;

/**
 * Copyright 2010 Gigadot [Weerapong Phadungsukanan]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * email : gigadot@gmail.com
 *
 */

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Rebound2 is a tool for looking up subclasses of a given superclass.
 *
 * TODO: Next version of rebound will save previous search results for faster execution.
 *
 * @author Weerapong Phadungsukanan
 */
public class Rebound2 {

    private String packagePrefix;
    private static boolean logging = false;
    private boolean includeAbstract;
    private boolean includeInterface;

    /**
     * Default Rebound2 constructor with empty prefix, the abstract classes and interfaces are both excluded from search
     * results.
     *
     * Use setIncludeAbstractAndInteface method to change this property.
     */
    public Rebound2() {
        this("");
    }

    /**
     * Construct a Rebound2 object from a given package prefix. Rebound2 excludes the abstract classes and interfaces in
     * the search results using this constructor. Use setIncludeAbstractAndInteface method to change this property.
     *
     * @param packagePrefix
     */
    public Rebound2(String packagePrefix) {
        this(packagePrefix, false);
    }

    /**
     * Construct a Rebound2 object from a given package prefix and set whether to include the abstract classes and
     * interfaces in the search results.
     *
     * @param packagePrefix
     * @param includeAbstractAndInteface
     */
    public Rebound2(String packagePrefix, boolean includeAbstractAndInteface) {
        this.packagePrefix = packagePrefix == null ? "" : packagePrefix;
        this.includeAbstract = includeAbstractAndInteface;
        this.includeInterface = includeAbstractAndInteface;
    }

    public Rebound2(String packagePrefix, boolean includeAbstract, boolean includeInteface) {
        this.packagePrefix = packagePrefix == null ? "" : packagePrefix;
        this.includeAbstract = includeAbstract;
        this.includeInterface = includeInteface;
    }

    /**
     * Set package prefix.
     *
     * @param packagePrefix
     */
    public void setPackagePrefix(String packagePrefix) {
        this.packagePrefix = packagePrefix == null ? "" : packagePrefix;
    }

    /**
     * Set whether to include the abstract classes and interfaces in the search results.
     *
     * @param includeAbstractAndInteface
     */
    public void setIncludeAbstractAndInteface(boolean includeAbstractAndInteface) {
        this.includeAbstract = includeAbstractAndInteface;
        this.includeInterface = includeAbstractAndInteface;
    }

    public void setIncludeAbstract(boolean includeAbstract) {
        this.includeAbstract = includeAbstract;
    }

    public void setIncludeInterface(boolean includeInterface) {
        this.includeInterface = includeInterface;
    }

    /**
     * Find subclasses of a given super class or interface.
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public <T> Set<Class<? extends T>> getSubClassesOf(final Class<T> clazz) {
        long start = System.currentTimeMillis();
        if (packagePrefix.isEmpty()) {
            if (logging) {
                System.out.println("Package prefix is empty. Rebound2 will look for subclasses inside "
                        + "every jar file under your classpath in this case. This is a lengthy "
                        + "and time consuming process. Please consider using Rebound2 with prefix.");
            }
        }
        final Set<Class<? extends T>> subClazzes = new HashSet<Class<? extends T>>();
        try {
            Set<Class> classes = getClassesUnderPackagePrefix(packagePrefix);
            for (Class clz : classes) {
                if (clazz.isAssignableFrom(clz)) {
                    subClazzes.add(clz);
                }
            }
        } catch (Exception ex) {
            if (logging) {
            	System.out.println(ex);
            }
        }
        if (!includeAbstract) {
            for (Iterator<Class<? extends T>> it = subClazzes.iterator(); it.hasNext();) {
                int modifiers = it.next().getModifiers();
                if (Modifier.isAbstract(modifiers)) {
                    it.remove();
                }
            }
        }
        if (!includeInterface) {
            for (Iterator<Class<? extends T>> it = subClazzes.iterator(); it.hasNext();) {
                int modifiers = it.next().getModifiers();
                if (Modifier.isInterface(modifiers)) {
                    it.remove();
                }
            }
        }
        long elapsed = System.currentTimeMillis() - start;
        if (logging) {
        	System.out.println("Rebound2 uses " + elapsed + " milliseconds to look up subclasses of " + clazz.getName() + " in package " + packagePrefix + ".*");
        }
        return subClazzes;
    }
    private Set<Class> temp_classes_set;

    /**
     * When use with empty package name, it look up inside every jar file. This is a lengthy and time consuming process.
     * Please consider using Rebound2 with prefix.
     *
     * @param packagePrefix
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private Set<Class> getClassesUnderPackagePrefix(final String packagePrefix) throws ClassNotFoundException, IOException {
        String packagePath = packagePrefix.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packagePath);

        temp_classes_set = new HashSet<Class>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (isJarFile(resource)) {
                // This should not be executed if the package prefix is empty.
                addClassesFromJarFile(resource, packagePrefix);
            } else {
                File file = toFile(resource);
                if (file.exists() && file.isDirectory()) {
                    addClassesFromDirectory(file, packagePrefix);
                }
            }
        }

        if (packagePrefix.isEmpty()) {
            String[] classPaths = System.getProperty("java.class.path").split(";");
            Set<String> classPathSet = new HashSet<String>(Arrays.asList(classPaths));
            for (String string : classPathSet) {
                File file = new File(string);
                if (file.exists() && file.isFile() && file.getName().endsWith(".jar")) {
                    URL jarUrl = toURL(file);
                    if (jarUrl != null) {
                        addClassesFromJarFile(jarUrl, packagePath);
                    }
                }
            }
        }

        return temp_classes_set;
    }

    private void addClassesFromDirectory(File directory, String packageName) throws ClassNotFoundException {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                String filename = file.getName();
                if (file.isDirectory()) {
                    addClassesFromDirectory(file, packageName + (packageName.isEmpty() ? "" : ".") + filename);
                } else if (filename.endsWith(".class")) {
                    temp_classes_set.add(Class.forName(packageName + (packageName.isEmpty() ? "" : ".") + filename.substring(0, filename.length() - 6)));
                }
            }
        }
    }

    private void addClassesFromJarFile(URL url, String packageName) {
        String packagePathFilter = packageName.replace('.', '/');
        Enumeration<JarEntry> allEntries = toJarFile(url).entries();
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        while (allEntries.hasMoreElements()) {
            JarEntry jarEntry = allEntries.nextElement();
            String entryName = jarEntry.getName();
            if (!jarEntry.isDirectory() && entryName.startsWith(packagePathFilter) && entryName.endsWith(".class")) {
                try {
                    String className = jarEntry.getName().replaceFirst("\\.class$", "").replace("/", ".");
                    temp_classes_set.add(Class.forName(className, false, contextClassLoader));
                } catch (Throwable ex) {
                    if (logging) {
                    	System.out.println("Cannot get Class for name : " + jarEntry.getName() + ex);
                    }
                }
            }
        }
    }

    private static boolean isJarFile(URL url) {
        return url.toString().startsWith("jar:file:");
    }

    private static File toFile(URL url) {
        try {
            String filepath = URLDecoder.decode(url.getFile(), "UTF-8");
            return new File(filepath);
        } catch (UnsupportedEncodingException ex) {
            if (logging) {
            	System.out.println("Unable to decode jar file location using UTF-8." + ex);
            }
        }
        if (logging) {
        	System.out.println("URL is not decoded : " + url.toString());
        }
        return new File(url.toString());
    }

    /**
     * Get a jar file object from given resource URL. The bits after !/ in jar URL is removed before processing it to
     * jar file object.
     *
     * @param url
     * @return
     */
    private static JarFile toJarFile(final URL url) {
        try {
            String rawJarPath = URLDecoder.decode(url.getFile(), "UTF-8");
            String jarPath = rawJarPath.replaceFirst("!.*$", "").replaceFirst("^file:", "");
            try {
                JarFile jarFile = new JarFile(jarPath);
                return jarFile;
            } catch (IOException ex) {
                if (logging) {
                	System.out.println("Cannot create JarFile object for path : " + jarPath + ex);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            if (logging) {
            	System.out.println("Unable to decode jar file location using UTF-8." + ex);
            }
        }
        if (logging) {
        	System.out.println("Null JarFile is returned.");
        }
        return null;
    }

    /**
     * Get a jar file object from given file object.
     *
     * @param url
     * @return
     */
    private static URL toURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (IOException ex) {
            if (logging) {
            	System.out.println("Cannot create JarFile object for path : " + file.getPath() + ex);
            }
        }
        if (logging) {
        	System.out.println("Null JarFile is returned.");
        }
        return null;
    }
}
