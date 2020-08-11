# hello-pedestal

FIXME: description

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar hello-pedestal-0.1.0-standalone.jar [args]

### Build an image

* Clone [eddumelendez/lein-paketo-buildpack](https://github.com/eddumelendez/lein-paketo-buildpack) repository
* Enter to `lein-paketo-buildpack` and perform `./scripts/build.sh`
* Install [pack](https://buildpacks.io/docs/install-pack)
* Perform the following command

```
pack build hello-pedestal \
--builder "gcr.io/paketo-buildpacks/builder:base" \
--buildpack gcr.io/paketo-buildpacks/amazon-corretto \
--buildpack <lein-paketo-buildpack-path> \
--buildpack paketo-buildpacks/executable-jar \
-e 'BP_JVM_VERSION=8.*' \
-e 'BP_LEIN_BUILT_ARTIFACT=target/uberjar/*-standalone.jar' -v
```

NOTE: Replace `<lein-paketo-buildpack-path>`

 * Run `docker run -p 8890:8890 hello-pedestal`
 * Hit the endpoint `http ":8890/hello"`

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
