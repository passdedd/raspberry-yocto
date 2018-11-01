SUMMARY = "Civetweb embedded web server"
HOMEPAGE = "https://github.com/civetweb/civetweb"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6f28fdcba0dda735eed62bac6a397562"

SRCREV = "19f31ba8dd8443e86c7028a4b4c37f4b299aa68c"
PV = "1.10+git${SRCPV}"
SRC_URI = "git://github.com/civetweb/civetweb.git \
           file://0001-Unittest-Link-librt-and-libm-using-l-option.patch \
           file://0001-undefine-macro-before-redefining.patch \
           "

S = "${WORKDIR}/git"

# civetweb supports building with make or cmake (although cmake lacks few features)
inherit cmake

# Disable Lua and Duktape because they do not compile from CMake (as of v1.8, v1.9 and v1.10).
# Disable ASAN as it is included only in Debug build.
EXTRA_OECMAKE = " \
    -DBUILD_SHARED_LIBS=ON \
    -DCIVETWEB_ENABLE_DUKTAPE=OFF \
    -DCIVETWEB_ENABLE_LUA=OFF \
    -DCIVETWEB_ENABLE_ASAN=OFF \
"
EXTRA_OECMAKE_class-native = " \
    -DBUILD_SHARED_LIBS=ON \
    -DCIVETWEB_ENABLE_DUKTAPE=OFF \
    -DCIVETWEB_ENABLE_LUA=OFF \
    -DCIVETWEB_ENABLE_ASAN=OFF \
"

# Building with ninja fails on missing third_party/lib/libcheck.a (which
# should come from external CMake project)
OECMAKE_GENERATOR = "Unix Makefiles"

PACKAGECONFIG ??= "caching ipv6 server ssl websockets"
PACKAGECONFIG[caching] = "-DCIVETWEB_DISABLE_CACHING=OFF,-DCIVETWEB_DISABLE_CACHING=ON,"
PACKAGECONFIG[cgi] = "-DCIVETWEB_DISABLE_CGI=OFF,-DCIVETWEB_DISABLE_CGI=ON,"
PACKAGECONFIG[cpp] = "-DCIVETWEB_ENABLE_CXX=ON,-DCIVETWEB_ENABLE_CXX=OFF,"
PACKAGECONFIG[debug] = "-DCIVETWEB_ENABLE_MEMORY_DEBUGGING=ON,-DCIVETWEB_ENABLE_MEMORY_DEBUGGING=OFF,"
PACKAGECONFIG[ipv6] = "-DCIVETWEB_ENABLE_IPV6=ON,-DCIVETWEB_ENABLE_IPV6=OFF,"
PACKAGECONFIG[server] = "-DCIVETWEB_INSTALL_EXECUTABLE=ON,-DCIVETWEB_INSTALL_EXECUTABLE=OFF,"
PACKAGECONFIG[ssl] = "-DCIVETWEB_ENABLE_SSL=ON -DCIVETWEB_SSL_OPENSSL_API_1_1=OFF -DCIVETWEB_ENABLE_SSL_DYNAMIC_LOADING=OFF,-DCIVETWEB_ENABLE_SSL=OFF,openssl (=1.0.2%),"
PACKAGECONFIG[websockets] = "-DCIVETWEB_ENABLE_WEBSOCKETS=ON,-DCIVETWEB_ENABLE_WEBSOCKETS=OFF,"

BBCLASSEXTEND = "native"
