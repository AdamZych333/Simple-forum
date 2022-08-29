import { Pipe, PipeTransform } from '@angular/core';
import { Comment } from 'src/app/core';

@Pipe({
  name: 'countChildren'
})
export class CountChildrenPipe implements PipeTransform {

  transform(parent: Comment): number {
    let count = 0;
    if(parent.children == null) return count;
    for(let child of parent.children){
      count+=this.transform(child);
    }

    return parent.children.length + count;
  }

}
