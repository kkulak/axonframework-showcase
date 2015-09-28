package knbit.events.bc.readmodel.config

import org.bson.Transformer
import org.joda.time.DateTime

/**
 * Created by novy on 28.09.15.
 */

class EnumTransformer implements Transformer {
    @Override
    Object transform(Object objectToTransform) {
        return objectToTransform.toString()
    }
}

class JodaEncoder implements Transformer {
    @Override
    Object transform(Object objectToTransform) {
        return new Date(((DateTime) objectToTransform).getMillis())
    }
}

class JodaDecoder implements Transformer {
    @Override
    Object transform(Object objectToTransform) {
        return new DateTime(((Date) objectToTransform).getTime())
    }
}
