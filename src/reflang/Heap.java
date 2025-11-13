package reflang;

/**
 * Representation of a heap, which maps references to values.
 * 
 * @author hridesh
 *
 */
public interface Heap {

	Value ref (Value value);

	Value deref (Value.RefVal loc);

	Value setref (Value.RefVal loc, Value value);

	Value free (Value.RefVal value);

	static public class Heap16Bit implements Heap {
		static final int HEAP_SIZE = 65_536;
		
		Value[] _rep = new Value[HEAP_SIZE];
		int index = 0;
		
		public Value ref (Value value) {
			if(index >= HEAP_SIZE)
				return new Value.DynamicError("Out of memory error");
			Value.RefVal new_loc = new Value.RefVal(index);
			_rep[index++] = value;
			return new_loc;
		}

		public Value deref (Value.RefVal loc) {
			try {
				if(_rep[loc.loc()] == null) 
					return new Value.DynamicError("Null pointer at " + loc.tostring());
				return _rep[loc.loc()];
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Value.DynamicError("Segmentation fault at access " + loc.tostring());
			}
		}

		public Value setref (Value.RefVal loc, Value value) {
			try {
				if(_rep[loc.loc()] == null) 
					return new Value.DynamicError("Null pointer at " + loc.tostring());
				return _rep[loc.loc()] = value;
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Value.DynamicError("Segmentation fault at access " + loc.tostring());
			}
		}

		public Value free (Value.RefVal loc) {
			try {
				_rep[loc.loc()] = null;
				return loc;
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Value.DynamicError("Segmentation fault at access " + loc.tostring());
			}
		}

		public Heap16Bit(){}

		// Method to print heap entries at index locations 0 to 4 using the Printer class
		public void showHeap() {
			Printer p = new Printer();
			for (int i = 0; i < 5; i++) {
				if (i < _rep.length) {
					System.out.print("loc: " + i + ": ");
					// TODO print the str to the reference (hexadecimal)
					if (_rep[i] != null) {
						p.print(_rep[i]);
					} else {
						System.out.println("null");
					}
				}
			}
		}

	}

}
