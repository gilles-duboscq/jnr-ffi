  
package jnr.ffi.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jnr.ffi.annotations.TypeDefinition;
import jnr.ffi.TypeAlias;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER, ElementType.METHOD })
@TypeDefinition(alias = TypeAlias.register_t)
public @interface register_t {

}