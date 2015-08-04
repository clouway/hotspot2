package com.clouway.anqp.adapter.http;

import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
@Service
@At("/testservice")
class ExampleService {

  @Get
  public Reply<?> serveExample() {
    return Reply.with("test message").ok();
  }

}
