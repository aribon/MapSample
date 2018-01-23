package me.aribon.mapsample.business.event;

import android.support.annotation.NonNull;

/**
 * @Author: aribon
 * @Date: 21/01/2018
 */

public abstract class Event<Content, Type> {

  private Type type;
  private Content content;

  public Event(Content content, @NonNull Type type) {
    this.content = content;
    this.type = type;
  }

  public Content getEventContent() {
    return content;
  }

  public Type getEventType() {
    return type;
  }

  public boolean hasContent() {
    return content != null;
  }

  public boolean equalsType(Type type) {
    return this.type == type;
  }

}
