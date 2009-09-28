
package com.kenai.jaffl.provider.jffi;

import com.kenai.jaffl.Address;
import com.kenai.jaffl.MemoryIO;
import com.kenai.jaffl.ParameterFlags;
import com.kenai.jaffl.Pointer;
import com.kenai.jaffl.byref.ByReference;
import com.kenai.jaffl.provider.AbstractArrayMemoryIO;
import com.kenai.jaffl.provider.DelegatingMemoryIO;
import com.kenai.jaffl.provider.InvocationSession;
import com.kenai.jaffl.provider.StringIO;
import com.kenai.jaffl.struct.Struct;
import com.kenai.jaffl.struct.StructUtil;
import com.kenai.jaffl.util.EnumMapper;
import com.kenai.jffi.InvocationBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class MarshalUtil {
    public static final com.kenai.jffi.MemoryIO IO = com.kenai.jffi.MemoryIO.getInstance();

    private MarshalUtil() {}

    public static final void marshal(InvocationBuffer buffer, byte[] array, int flags) {
        if (array == null) {
            buffer.putAddress(0L);
        } else {
            buffer.putArray(array, 0, array.length, flags);
        }
    }

    public static final void marshal(InvocationBuffer buffer, short[] array, int flags) {
        if (array == null) {
            buffer.putAddress(0L);
        } else {
            buffer.putArray(array, 0, array.length, flags);
        }
    }

    public static final void marshal(InvocationBuffer buffer, int[] array, int flags) {
        if (array == null) {
            buffer.putAddress(0L);
        } else {
            buffer.putArray(array, 0, array.length, flags);
        }
    }

    public static final void marshal(InvocationBuffer buffer, long[] array, int flags) {
        if (array == null) {
            buffer.putAddress(0L);
        } else {
            buffer.putArray(array, 0, array.length, flags);
        }
    }

    public static final void marshal(InvocationBuffer buffer, float[] array, int flags) {
        if (array == null) {
            buffer.putAddress(0L);
        } else {
            buffer.putArray(array, 0, array.length, flags);
        }
    }

    public static final void marshal(InvocationBuffer buffer, double[] array, int flags) {
        if (array == null) {
            buffer.putAddress(0L);
        } else {
            buffer.putArray(array, 0, array.length, flags);
        }
    }

    public static final void marshal(InvocationBuffer buffer, Pointer ptr, int nativeArrayFlags) {
        if (ptr == null) {
            buffer.putAddress(0L);
        } else if (ptr instanceof JFFIPointer) {
            buffer.putAddress(((JFFIPointer) ptr).address);
        } else if (ptr instanceof AbstractArrayMemoryIO) {
            AbstractArrayMemoryIO aio = (AbstractArrayMemoryIO) ptr;
            buffer.putArray(aio.array(), aio.offset(), aio.length(), nativeArrayFlags);
        } else {
            throw new IllegalArgumentException("unsupported argument type" + ptr.getClass());
        }
    }

    public static final void marshal(InvocationBuffer buffer, Address ptr) {
        if (ptr == null) {
            buffer.putAddress(0L);
        } else {
            buffer.putAddress(ptr.nativeAddress());
        }
    }

    public static final void marshal(InvocationBuffer buffer, ByteBuffer buf, int flags) {
        if (buf == null) {
            buffer.putAddress(0L);
        } else if (buf.hasArray()) {
            buffer.putArray(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), flags);
        } else {
            buffer.putDirectBuffer(buf, buf.position(), buf.remaining());
        }
    }

    public static final void marshal(InvocationBuffer buffer, ShortBuffer buf, int flags) {
        if (buf == null) {
            buffer.putAddress(0L);
        } else if (buf.hasArray()) {
            buffer.putArray(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), flags);
        } else {
            buffer.putDirectBuffer(buf, buf.position() << 1, buf.remaining() << 1);
        }
    }

    public static final void marshal(InvocationBuffer buffer, IntBuffer buf, int flags) {
        if (buf == null) {
            buffer.putAddress(0L);
        } else if (buf.hasArray()) {
            buffer.putArray(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), flags);
        } else {
            buffer.putDirectBuffer(buf, buf.position() << 2, buf.remaining() << 2);
        }
    }

    public static final void marshal(InvocationBuffer buffer, LongBuffer buf, int flags) {
        if (buf == null) {
            buffer.putAddress(0L);
        } else if (buf.hasArray()) {
            buffer.putArray(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), flags);
        } else {
            buffer.putDirectBuffer(buf, buf.position() << 3, buf.remaining() << 3);
        }
    }

    public static final void marshal(InvocationBuffer buffer, FloatBuffer buf, int flags) {
        if (buf == null) {
            buffer.putAddress(0L);
        } else if (buf.hasArray()) {
            buffer.putArray(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), flags);
        } else {
            buffer.putDirectBuffer(buf, buf.position() << 2, buf.remaining() << 2);
        }
    }

    public static final void marshal(InvocationBuffer buffer, DoubleBuffer buf, int flags) {
        if (buf == null) {
            buffer.putAddress(0L);
        } else if (buf.hasArray()) {
            buffer.putArray(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), flags);
        } else {
            buffer.putDirectBuffer(buf, buf.position() << 3, buf.remaining() << 3);
        }
    }

    public static final void marshal(InvocationBuffer buffer, CharSequence cs) {
        if (cs == null) {
            buffer.putAddress(0L);
        } else {
            ByteBuffer buf = StringIO.getStringIO().toNative(cs, cs.length(), true);
            buffer.putArray(buf.array(), buf.arrayOffset(), buf.remaining(), com.kenai.jffi.ArrayFlags.IN | com.kenai.jffi.ArrayFlags.NULTERMINATE);
        }
    }

    public static final void marshal(InvocationBuffer buffer, Struct parameter, int parameterFlags, int nativeArrayFlags) {
        if (parameter == null) {
            buffer.putAddress(0L);
        } else {
            Struct s = parameter;
            MemoryIO io = StructUtil.getMemoryIO(s, parameterFlags);
            if (io instanceof AbstractArrayMemoryIO) {
                AbstractArrayMemoryIO aio = (AbstractArrayMemoryIO) io;
                buffer.putArray(aio.array(), aio.offset(), aio.length(), nativeArrayFlags);
            } else if (io.isDirect()) {
                buffer.putAddress(io.getAddress());
            }
        }
    }

    public static final void marshal(InvocationBuffer buffer, Struct[] parameter, int parameterFlags, int nativeArrayFlags) {
        if (parameter == null) {
            buffer.putAddress(0L);
        } else {
            Struct[] array = parameter;
            MemoryIO io = StructUtil.getMemoryIO(array[0], parameterFlags);
            if (!(io instanceof DelegatingMemoryIO)) {
                throw new RuntimeException("Struct array must be backed by contiguous array");
            }
            io = ((DelegatingMemoryIO) io).getDelegatedMemoryIO();
            if (io instanceof AbstractArrayMemoryIO) {
                AbstractArrayMemoryIO aio = (AbstractArrayMemoryIO) io;
                buffer.putArray(aio.array(), aio.offset(), aio.length(), nativeArrayFlags);
            } else if (io.isDirect()) {
                buffer.putAddress(io.getAddress());
            }
        }
    }

    public static final void marshal(InvocationSession session, InvocationBuffer buffer, ByReference parameter, int flags) {
        if (parameter == null) {
            buffer.putAddress(0L);
        } else {
            final ByReference ref = (ByReference) parameter;
            final ByteBuffer buf = ByteBuffer.allocate(ref.nativeSize()).order(ByteOrder.nativeOrder());
            buf.clear();
            if (com.kenai.jffi.ArrayFlags.isIn(flags)) {
                ref.marshal(buf);
            }
            buffer.putArray(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), flags);
            if (com.kenai.jffi.ArrayFlags.isOut(flags)) {
                session.addPostInvoke(new InvocationSession.PostInvoke() {
                    public void postInvoke() {
                        ref.unmarshal(buf);
                    }
                });
            }
        }
    }

    public static final void marshal(InvocationSession session, InvocationBuffer buffer, StringBuilder parameter, int inout, int nflags) {
        if (parameter == null) {
            buffer.putAddress(0L);
        } else {
            final StringBuilder sb = parameter;
            final StringIO io = StringIO.getStringIO();
            final ByteBuffer buf = io.toNative(sb, sb.capacity(), ParameterFlags.isIn(inout));
            buffer.putArray(buf.array(), buf.arrayOffset(), buf.remaining(), nflags);
            //
            // Copy the string back out if its an OUT parameter
            //
            if (ParameterFlags.isOut(inout)) {
                session.addPostInvoke(new InvocationSession.PostInvoke() {

                    public void postInvoke() {
                        sb.delete(0, sb.length()).append(io.fromNative(buf, sb.capacity()));
                    }
                });
            }
        }
    }
    
    public static final void marshal(InvocationSession session, InvocationBuffer buffer, final StringBuffer parameter, int inout, int nflags) {
        if (parameter == null) {
            buffer.putAddress(0L);
        } else {
            final StringBuffer sb = parameter;
            final StringIO io = StringIO.getStringIO();
            final ByteBuffer buf = io.toNative(sb, sb.capacity(), ParameterFlags.isIn(inout));
            buffer.putArray(buf.array(), buf.arrayOffset(), buf.remaining(), nflags);
            //
            // Copy the string back out if its an OUT parameter
            //
            if (ParameterFlags.isOut(inout)) {
                session.addPostInvoke(new InvocationSession.PostInvoke() {

                    public void postInvoke() {
                        sb.delete(0, sb.length()).append(io.fromNative(buf, sb.capacity()));
                    }
                });
            }
        }
    }

    public static final void marshal(InvocationBuffer buffer, final Enum parameter) {
        buffer.putInt(EnumMapper.getInstance().intValue(parameter));
    }

    public static final String returnString(long ptr) {
        if (ptr == 0) {
            return null;
        }
        final ByteBuffer buf = ByteBuffer.wrap(IO.getZeroTerminatedByteArray(ptr));

        return StringIO.getStringIO().fromNative(buf).toString();
    }

    public static final MemoryIO newMemoryIO(long ptr) {
        return ptr == 0 ? null : new DirectMemoryIO(ptr);
    }

    public static final void useMemory(long ptr, Struct s) {
        s.useMemory(new DirectMemoryIO(ptr));
    }

    public static final Struct returnStruct(long ptr, Struct s) {
        if (ptr == 0) {
            return null;
        }
        s.useMemory(new DirectMemoryIO(ptr));
        return s;
    }
}