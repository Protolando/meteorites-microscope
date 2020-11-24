import { IMicroscopePicture } from 'app/shared/model/microscope-picture.model';
import { IRock } from 'app/shared/model/rock.model';

export interface IMicroscope {
  id?: number;
  name?: string;
  microscopePictures?: IMicroscopePicture[];
  rock?: IRock;
}

export class Microscope implements IMicroscope {
  constructor(public id?: number, public name?: string, public microscopePictures?: IMicroscopePicture[], public rock?: IRock) {}
}
