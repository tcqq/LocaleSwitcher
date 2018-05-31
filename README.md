[ ![Download](https://api.bintray.com/packages/tcqq/android/localeswitcher/images/download.svg?version=1.1.0) ](https://bintray.com/tcqq/android/localeswitcher/1.1.0/link)
[![API](https://img.shields.io/badge/API-17%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=17)
[![Licence](https://img.shields.io/badge/Licence-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

LocaleSwitcher
==============

The LocaleSwitcher allows your application to support multiple language switching.

Introduction
------------

* Persistent language storage.
* Supports manual setting of language or automatic language.
* When the **A** Activity language changes, then back to the **B** Activity, language will updated synchronously.

Screenshots
-----------

![Language Settings](/screenshots/language_settings.png)
![Main](/screenshots/main.png)

Setup
-----

#### build.gradle
```
repositories {
    jcenter()
    maven { url 'https://dl.bintray.com/tcqq/android' } //For Snapshots
}
```
```
dependencies {
    // Using JCenter
    implementation 'com.tcqq.android.localeswitcher:localeswitcher:1.1.0'
}
```
#### Stay Updated
|LocaleSwitcher|
|---|
|<div align="center">1.1.0</div>
|<a href='https://bintray.com/tcqq/android/localeswitcher?source=watch' alt='Get automatic notifications about new "localeswitcher" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>

License
-------

Copyright 2018 Tcqq.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
